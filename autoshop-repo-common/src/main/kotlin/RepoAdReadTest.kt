package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.AsAdId
import ru.drvshare.autoshop.common.models.AsError
import ru.drvshare.autoshop.common.repo.DbAdIdRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoAdReadTest {
    abstract val repo: IAdRepository

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.readAd(DbAdIdRequest(successId)) }

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() {
        val result = runBlocking { repo.readAd(DbAdIdRequest(notFoundId)) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        assertEquals(
            listOf(AsError(field = "id", message = "Not Found")),
            result.errors
        )
    }

    companion object: BaseInitAds("search") {
        override val initObjects: List<AsAd> = listOf(
            createInitTestModel("read")
        )
        private val readSuccessStub = initObjects.first()

        val successId = AsAdId(readSuccessStub.id.asString())
        val notFoundId = AsAdId("ad-repo-read-notFound")

    }
}
