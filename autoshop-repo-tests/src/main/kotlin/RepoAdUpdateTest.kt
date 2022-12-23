package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected val updateSucc = initObjects[0]
    protected val updateConc = initObjects[1]
    protected val updateIdNotFound = AsAdId("ad-repo-update-not-found")
    protected val lockBad = AsAdLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = AsAdLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc = AsAd(
        id = updateSucc.id,
        title = "update object",
        description = "update object description",
        ownerId = AsUserId("owner-123"),
        visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
        adType = EAsDealSide.SUPPLY,
        lock = initObjects.first().lock,
    )
    private val reqUpdateNotFound = AsAd(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        ownerId = AsUserId("owner-123"),
        visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
        adType = EAsDealSide.SUPPLY,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc = AsAd(
        id = updateConc.id,
        title = "update object not found",
        description = "update object not found description",
        ownerId = AsUserId("owner-123"),
        visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
        adType = EAsDealSide.SUPPLY,
        lock = lockBad,
    )

    @Test
    fun updateSuccess() = runTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.adType, result.data?.adType)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object: BaseInitAds("update") {
        override val initObjects: List<AsAd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
