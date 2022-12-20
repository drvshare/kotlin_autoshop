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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val command = EAsCommand.UPDATE
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
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd,
            )
        },
        invokeUpdateAd = {
            DbAdResponse(
                isSuccess = true,
                data = AsAd(
                    id = AsAdId("123"),
                    title = "xyz",
                    description = "xyz",
                    adType = EAsDealSide.DEMAND,
                    visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    ) }
    private val settings by lazy {
        AsSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { AsAdProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = AsAd(
            id = AsAdId("123"),
            title = "xyz",
            description = "xyz",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
            lock = AsAdLock(uuidOld),
        )
        val ctx = AsAdContext(
            command = command,
            state = EAsState.NONE,
            workMode = EAsWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(EAsState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.adResponse.id)
        assertEquals(adToUpdate.title, ctx.adResponse.title)
        assertEquals(adToUpdate.description, ctx.adResponse.description)
        assertEquals(adToUpdate.adType, ctx.adResponse.adType)
        assertEquals(adToUpdate.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
