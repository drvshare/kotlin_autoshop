package ru.drvshare.autoshop.backend.repo.sql

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import ru.drvshare.autoshop.common.models.*
import java.util.*

object AdsTable : StringIdTable("Ads") {
    val title = varchar("title", 128)
    val description = varchar("description", 512)
    val ownerId = reference("owner_id", UsersTable.id).index()
    val visibility = enumeration("visibility", EAsAdVisibility::class)
    val adType = enumeration("deal_side", EAsDealSide::class).index()
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    // Mapper functions from sql-like table to AsAd
    fun from(res: InsertStatement<Number>) = AsAd(
        id = AsAdId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = AsUserId(res[ownerId].toString()),
        visibility = res[visibility],
        adType = res[adType],
        lock = AsAdLock(res[lock])
    )

    fun from(res: ResultRow) = AsAd(
        id = AsAdId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = AsUserId(res[ownerId].toString()),
        visibility = res[visibility],
        adType = res[adType],
        lock = AsAdLock(res[lock])
    )
}

object UsersTable : StringIdTable("Users") {
    override val primaryKey = PrimaryKey(AdsTable.id)
}

open class StringIdTable(name: String = "", columnName: String = "id", columnLength: Int = 50) : IdTable<String>(name) {
    override val id: Column<EntityID<String>> =
        varchar(columnName, columnLength).uniqueIndex().default(generateUuidAsStringFixedSize())
            .entityId()
    override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}

fun generateUuidAsStringFixedSize() = UUID.randomUUID().toString().replace("-", "").substring(0, 9)
