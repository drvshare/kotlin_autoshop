package ru.drvshare.autoshop.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoStub
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Suppress("TestMethodWithoutAssertion")
@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = EAsCommand.SEARCH
    private val settings by lazy {
        AsSettings(
            repoTest = AdRepoStub()
        )
    }

    private val processor by lazy { AsAdProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = AsAdContext(
            command = command,
            state = EAsState.NONE,
            workMode = EAsWorkMode.TEST,
            adFilterRequest = AsAdFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(EAsState.FAILING, ctx.state)
    }
}

