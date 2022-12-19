package ru.drvshare.autoshop.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.common.models.AsUserId
import ru.drvshare.autoshop.common.repo.DbAdFilterRequest
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoAdSearchTest {
    abstract val repo: IAdRepository
    @Test
    fun searchOwner() {
        val result = runBlocking { repo.searchAd(DbAdFilterRequest(ownerId = searchOwnerId)) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[1], initObjects[3])
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchDealSide() {
        val result = runBlocking { repo.searchAd(DbAdFilterRequest(dealSide = EAsDealSide.SUPPLY)) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[2], initObjects[4])
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        val searchOwnerId = AsUserId("owner-124")
        override val initObjects: List<AsAd> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", adType = EAsDealSide.SUPPLY),
            createInitTestModel("ad4", ownerId = searchOwnerId),
            createInitTestModel("ad5", adType = EAsDealSide.SUPPLY),
        )
    }
}
