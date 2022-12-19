package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.createAd(DbAdRequest(createObj)) }
        val expected = createObj.copy(id = result.data?.id ?: AsAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.adType, result.data?.adType)
        assertNotEquals(AsAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        private val createObj = AsAd(
            title = "create object",
            description = "create object description",
            ownerId = AsUserId("owner-123"),
            visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
            adType = EAsDealSide.SUPPLY,
        )
        override val initObjects: List<AsAd> = emptyList()
    }
}
