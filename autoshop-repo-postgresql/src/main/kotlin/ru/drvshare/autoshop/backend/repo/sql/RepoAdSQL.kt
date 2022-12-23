package ru.drvshare.autoshop.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.*
import ru.drvshare.autoshop.common.helpers.errorRepoConcurrency
import java.sql.SQLException
import java.util.NoSuchElementException

private const val notFoundCode = "not-found"

class RepoAdSQL(
    url: String = "jdbc:postgresql://localhost:5432/autoshopdevdb",
    user: String = "postgres",
    password: String = "autoshop-pass",
    schema: String = "autoshop",
    initObjects: Collection<AsAd> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IAdRepository {
    private val db by lazy { SqlConnector(url, user, password, schema).connect(AdsTable, UsersTable) }

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(item: AsAd): DbAdResponse {
        return safeTransaction({
            val realOwnerId = UsersTable.insertIgnore {
                if (item.ownerId != AsUserId.NONE) {
                    it[id] = item.ownerId.asString()
                }
            } get UsersTable.id

            val res = AdsTable.insert {
                if (item.id != AsAdId.NONE) {
                    it[id] = item.id.asString()
                }
                it[title] = item.title
                it[description] = item.description
                it[ownerId] = realOwnerId
                it[visibility] = item.visibility
                it[adType] = item.adType
                it[lock] = item.lock.asString()
            }

            DbAdResponse(AdsTable.from(res), true)
        }, {
            DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(AsError(message = message ?: localizedMessage))
            )
        })
    }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val ad = rq.ad.copy(lock = AsAdLock(randomUuid()))
        return save(ad)
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return safeTransaction({
            val result = (AdsTable innerJoin UsersTable).select { AdsTable.id.eq(rq.id.asString()) }.single()

            DbAdResponse(AdsTable.from(result), true)
        }, {
            val err = when (this) {
                is NoSuchElementException -> AsError(field = "id", message = "Not Found", code = notFoundCode)
                is IllegalArgumentException -> AsError(message = "More than one element with the same id")
                else -> AsError(message = localizedMessage)
            }
            DbAdResponse(data = null, isSuccess = false, errors = listOf(err))
        })
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != AsAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.ad.lock.takeIf { it != AsAdLock.NONE }?.asString()
        val newAd = rq.ad.copy(lock = AsAdLock(randomUuid()))

        return safeTransaction({
            val local = AdsTable.select { AdsTable.id.eq(key) }.singleOrNull()?.let {
                AdsTable.from(it)
            } ?: return@safeTransaction resultErrorNotFound

            return@safeTransaction when (oldLock) {
                null, local.lock.asString() -> updateDb(newAd)
                else -> resultErrorConcurrent(local.lock.asString(), local)
            }
        }, {
            DbAdResponse(
                data = rq.ad,
                isSuccess = false,
                errors = listOf(AsError(field = "id", message = "Not Found", code = notFoundCode))
            )
        })

    }

    private fun updateDb(newAd: AsAd): DbAdResponse {
        UsersTable.insertIgnore {
            if (newAd.ownerId != AsUserId.NONE) {
                it[id] = newAd.ownerId.asString()
            }
        }

        AdsTable.update({ AdsTable.id.eq(newAd.id.asString()) }) {
            it[title] = newAd.title
            it[description] = newAd.description
            it[ownerId] = newAd.ownerId.asString()
            it[visibility] = newAd.visibility
            it[adType] = newAd.adType
            it[lock] = newAd.lock.asString()
        }
        val result = AdsTable.select { AdsTable.id.eq(newAd.id.asString()) }.single()

        return DbAdResponse(data = AdsTable.from(result), isSuccess = true)
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != AsAdId.NONE }?.asString() ?: return resultErrorEmptyId

        return safeTransaction({
            val local = AdsTable.select { AdsTable.id.eq(key) }.single().let { AdsTable.from(it) }
            if (local.lock == rq.lock) {
                AdsTable.deleteWhere { AdsTable.id eq rq.id.asString() }
                DbAdResponse(data = local, isSuccess = true)
            } else {
                resultErrorConcurrent(rq.lock.asString(), local)
            }
        }, {
            DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(AsError(field = "id", message = "Not Found"))
            )
        })
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return safeTransaction({
            // Select only if options are provided
            val results = (AdsTable innerJoin UsersTable).select {
                (if (rq.ownerId == AsUserId.NONE) Op.TRUE else AdsTable.ownerId eq rq.ownerId.asString()) and
                        (
                                if (rq.titleFilter.isBlank()) Op.TRUE else (AdsTable.title like "%${rq.titleFilter}%") or
                                        (AdsTable.description like "%${rq.titleFilter}%")
                                ) and
                        (if (rq.dealSide == EAsDealSide.NONE) Op.TRUE else AdsTable.adType eq rq.dealSide)
            }

            DbAdsResponse(data = results.map { AdsTable.from(it) }, isSuccess = true)
        }, {
            DbAdsResponse(data = emptyList(), isSuccess = false, listOf(AsError(message = localizedMessage)))
        })
    }

    /**
     * Transaction wrapper to safely handle caught exception and throw all sql-like exceptions. Also remove lot's of duplication code
     */
    private fun <T> safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T {
        return try {
            transaction(db, statement)
        } catch (e: SQLException) {
            throw e
        } catch (e: Throwable) {
            return handleException(e)
        }
    }

    companion object {
        val resultErrorEmptyId = DbAdResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                AsError(
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )

        fun resultErrorConcurrent(lock: String, ad: AsAd?) = DbAdResponse(
            data = ad,
            isSuccess = false,
            errors = listOf(
                errorRepoConcurrency(AsAdLock(lock), ad?.lock?.let { AsAdLock(it.asString()) }
                )
            ))

        val resultErrorNotFound = DbAdResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                AsError(
                    field = "id",
                    message = "Not Found",
                    code = notFoundCode
                )
            )
        )
    }
}
