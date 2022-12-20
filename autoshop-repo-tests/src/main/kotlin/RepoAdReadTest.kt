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
abstract class RepoAdReadTest {
    abstract val repo: IAdRepository

    @Test
    fun readSuccess() = runTest {
        val result = repo.readAd(DbAdIdRequest(successId))

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object: BaseInitAds("delete") {
        override val initObjects: List<AsAd> = listOf(
            createInitTestModel("read")
        )
        private val readSuccessStub = initObjects.first()

        val successId = AsAdId(readSuccessStub.id.asString())
        val notFoundId = AsAdId("ad-repo-read-notFound")

    }
}
