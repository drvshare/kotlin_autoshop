package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    protected open val lockNew: AsAdLock = AsAdLock("20000000-0000-0000-0000-000000000002")

    protected val createObj = AsAd(
        title = "create object",
        description = "create object description",
        ownerId = AsUserId("owner-123"),
        visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
        adType = EAsDealSide.SUPPLY,
    )

    @Test
    fun createSuccess() = runTest{
        val result =  repo.createAd(DbAdRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: AsAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.adType, result.data?.adType)
        assertNotEquals(AsAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object: BaseInitAds("create") {
        override val initObjects: List<AsAd> = emptyList()
    }
}
