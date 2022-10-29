package ru.drvshare.autoshop.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class AdSearchStubTest {

    private val processor = AsAdProcessor()
    private val filter = AsAdFilter(searchString = "Нива")

    @Test
    fun read() = runTest {

        val ctx = AsAdContext(
            command = EAsCommand.SEARCH,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.SUCCESS,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (AsAdStub.get()) {
            assertEquals(adType, first.adType)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.SEARCH,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_ID,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.SEARCH,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.DB_ERROR,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.SEARCH,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_TITLE,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
