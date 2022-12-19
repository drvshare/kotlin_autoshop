package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.updateAd(DbAdRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj.id, result.data?.id)
        assertEquals(updateObj.title, result.data?.title)
        assertEquals(updateObj.description, result.data?.description)
        assertEquals(updateObj.adType, result.data?.adType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.updateAd(DbAdRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        assertEquals(listOf(AsError(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitAds("search") {
        override val initObjects: List<AsAd> = listOf(
            createInitTestModel("update")
        )
        private val updateId = initObjects.first().id
        private val updateIdNotFound = AsAdId("ad-repo-update-not-found")

        private val updateObj = AsAd(
            id = updateId,
            title = "update object",
            description = "update object description",
            ownerId = AsUserId("owner-123"),
            visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
            adType = EAsDealSide.SUPPLY,
        )

        private val updateObjNotFound = AsAd(
            id = updateIdNotFound,
            title = "update object not found",
            description = "update object not found description",
            ownerId = AsUserId("owner-123"),
            visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
            adType = EAsDealSide.SUPPLY,
        )
    }
}
