package ru.drvshare.autoshop.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.backend.repo.common.AdRepositoryMock
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.DbAdResponse
import ru.drvshare.autoshop.common.repo.IAdRepository
import kotlin.test.assertEquals

private val initAd = AsAd(
    id = AsAdId("123"),
    title = "abc",
    description = "abc",
    adType = EAsDealSide.DEMAND,
    visibility = EAsAdVisibility.VISIBLE_PUBLIC,
)
private val uuid = "10000000-0000-0000-0000-000000000001"
private val repo: IAdRepository
    get() = AdRepositoryMock(
        invokeReadAd = {
            if (it.id == initAd.id) {
                DbAdResponse(
                    isSuccess = true,
                    data = initAd,
                )
            } else DbAdResponse(
                isSuccess = false,
                data = null,
                errors = listOf(AsError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    AsSettings(
        repoTest = repo
    )
}
private val processor by lazy { AsAdProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: EAsCommand) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("12345"),
            title = "xyz",
            description = "xyz",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_TO_GROUP,
            lock = AsAdLock(uuid),
        ),
    )
    processor.exec(ctx)
    assertEquals(EAsState.FAILING, ctx.state)
    assertEquals(AsAd(), ctx.adResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
