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
fun validationTitleCorrect(command: EAsCommand, processor: AsAdProcessor) = runTest {
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
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EAsState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123"),
            title = " \n\t abc \t\n ",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EAsState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123"),
            title = "",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EAsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: EAsCommand, processor: AsAdProcessor) = runTest {
    val ctx = AsAdContext(
        command = command,
        state = EAsState.NONE,
        workMode = EAsWorkMode.TEST,
        adRequest = AsAd(
            id = AsAdId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            adType = EAsDealSide.DEMAND,
            visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EAsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
