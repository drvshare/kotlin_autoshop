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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val command = EAsCommand.DELETE
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidBad = "10000000-0000-0000-0000-000000000003"
    private val initAd = AsAd(
        id = AsAdId("123"),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.DEMAND,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        lock = AsAdLock(uuidOld),
    )
    private val repo by lazy {
        AdRepositoryMock(
            invokeReadAd = {
               DbAdResponse(
                   isSuccess = true,
                   data = initAd,
               )
            },
            invokeDeleteAd = {
                if (it.id == initAd.id)
                    DbAdResponse(
                        isSuccess = true,
                        data = initAd
                    )
                else DbAdResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        AsSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { AsAdProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToUpdate = AsAd(
            id = AsAdId("123"),
            lock = AsAdLock(uuidOld)
        )
        val ctx = AsAdContext(
            command = command,
            state = EAsState.NONE,
            workMode = EAsWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(EAsState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.title, ctx.adResponse.title)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adType, ctx.adResponse.adType)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
