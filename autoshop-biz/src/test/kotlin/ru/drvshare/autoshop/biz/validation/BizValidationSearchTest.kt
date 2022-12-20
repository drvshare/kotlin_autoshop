package ru.drvshare.autoshop.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsAdFilter
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.models.EAsWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = EAsCommand.SEARCH
    private val processor by lazy { AsAdProcessor() }

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

