package ru.drvshare.autoshop.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.backend.repo.common.AdRepositoryMock
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdResponse
import ru.drvshare.autoshop.common.repo.DbAdsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoOffersTest {

    private val command = EAsCommand.OFFERS
    private val initAd = AsAd(
        id = AsAdId("123"),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.DEMAND,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
    )
    private val noneTypeAd = AsAd(
        id = AsAdId("213"),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.NONE,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
    )
    private val offerAd = AsAd(
        id = AsAdId("321"),
        title = "abcd",
        description = "xyz",
        adType = EAsDealSide.SUPPLY,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd
            )
        },
        invokeSearchAd = {
            DbAdsResponse(
                isSuccess = true,
                data = listOf(offerAd)
            )
        }
    ) }
    private val settings by lazy {
        AsSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { AsAdProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersSuccessTest() = runTest {
        val ctx = AsAdContext(
            command = command,
            state = EAsState.NONE,
            workMode = EAsWorkMode.TEST,
            adRequest = AsAd(
                id = AsAdId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(EAsState.FINISHING, ctx.state)
        assertEquals(1, ctx.adsResponse.size)
        assertEquals(EAsDealSide.SUPPLY, ctx.adsResponse.first().adType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersNotFoundTest() = repoNotFoundTest(command)
}
