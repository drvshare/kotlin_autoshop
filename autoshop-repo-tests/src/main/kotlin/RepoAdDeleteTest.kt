package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.AsAdId
import ru.drvshare.autoshop.common.repo.DbAdIdRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdDeleteTest {
    abstract val repo: IAdRepository

    @Test
    fun deleteSuccess() = runTest {
        val result = repo.deleteAd(DbAdIdRequest(successId, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runTest {
        val result = repo.deleteAd(DbAdIdRequest(concurrencyId, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object: BaseInitAds("delete") {
        override val initObjects: List<AsAd> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val successId = AsAdId(initObjects.first().id.asString())
        val notFoundId = AsAdId("ad-repo-delete-notFound")
        val concurrencyId = AsAdId(initObjects[1].id.asString())
    }
}
