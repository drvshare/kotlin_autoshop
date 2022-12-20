package ru.drvshare.autoshop.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockCorrect(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EAsState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockTrim(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock(" \n\t 123-234-abc-ABC \n\t "),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EAsState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockEmpty(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock(""),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EAsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockFormat(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EAsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}
