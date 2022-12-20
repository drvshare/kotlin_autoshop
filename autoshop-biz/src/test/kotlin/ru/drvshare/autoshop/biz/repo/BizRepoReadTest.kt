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

class BizRepoReadTest {

    private val command = EAsCommand.READ
    private val initAd = AsAd(
        id = AsAdId("123"),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.DEMAND,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd,
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
    fun repoReadSuccessTest() = runTest {
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
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.title, ctx.adResponse.title)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adType, ctx.adResponse.adType)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
