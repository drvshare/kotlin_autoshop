package ru.drvshare.autoshop.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.backend.repo.common.AdRepositoryMock
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val command = EAsCommand.SEARCH
    private val initAd = AsAd(
        id = AsAdId("123"),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.DEMAND,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeSearchAd = {
            DbAdsResponse(
                isSuccess = true,
                data = listOf(initAd),
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
    fun repoSearchSuccessTest() = runTest {
        val ctx = AsAdContext(
            command = command,
            state = EAsState.NONE,
            workMode = EAsWorkMode.TEST,
            adFilterRequest = AsAdFilter(
                searchString = "ab",
                dealSide = EAsDealSide.DEMAND
            ),
        )
        processor.exec(ctx)
        assertEquals(EAsState.FINISHING, ctx.state)
        assertEquals(1, ctx.adsResponse.size)
    }
}
