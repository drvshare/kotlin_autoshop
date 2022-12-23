package ru.drvshare.autoshop.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.backend.repo.common.AdRepositoryMock
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val command = EAsCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = AdRepositoryMock(
        invokeCreateAd = {
            DbAdResponse(
                isSuccess = true,
                data = AsAd(
                    id = AsAdId(uuid),
                    title = it.ad.title,
                    description = it.ad.description,
                    adType = it.ad.adType,
                    visibility = it.ad.visibility,
                )
            )
        }
    )
    private val settings = AsSettings(
        repoTest = repo
    )
    private val processor = AsAdProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = AsAdContext(
            command = command,
            state = EAsState.NONE,
            workMode = EAsWorkMode.TEST,
            adRequest = AsAd(
                id = AsAdId("123"),
                title = "abc",
                description = "abc",
                adType = EAsDealSide.DEMAND,
                visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(EAsState.FINISHING, ctx.state)
        assertNotEquals(AsAdId.NONE, ctx.adResponse.id)
        assertEquals("abc", ctx.adResponse.title)
        assertEquals("abc", ctx.adResponse.description)
        assertEquals(EAsDealSide.DEMAND, ctx.adResponse.adType)
        assertEquals(EAsAdVisibility.VISIBLE_PUBLIC, ctx.adResponse.visibility)
    }
}
