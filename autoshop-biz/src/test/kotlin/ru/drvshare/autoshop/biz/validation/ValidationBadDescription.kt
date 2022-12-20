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
fun validationDescriptionCorrect(command: EAsCommand, processor: AsAdProcessor) = runTest {
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
            lock = AsAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EAsState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123"),
            title = "abc",
            description = " \n\tabc \n\t",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EAsState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123"),
            title = "abc",
            description = "",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EAsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123"),
            title = "abc",
            description = "!@#$%^&*(),.{}",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            lock = AsAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EAsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
