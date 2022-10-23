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
class AdOffersStubTest {

    private val processor = AsAdProcessor()
    private val id = AsAdId("777")

    @Test
    fun offers() = runTest {

        val ctx = AsAdContext(
            command = EAsCommand.OFFERS,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.SUCCESS,
            adRequest = AsAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.adResponse.id)

        with(AsAdStub.get()) {
            assertEquals(title, ctx.adResponse.title)
            assertEquals(description, ctx.adResponse.description)
            assertEquals(adType, ctx.adResponse.adType)
            assertEquals(visibility, ctx.adResponse.visibility)
        }

        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(ctx.adResponse.title))
        assertTrue(first.description.contains(ctx.adResponse.title))
        assertEquals(EAsDealSide.SUPPLY, first.adType)
        assertEquals(AsAdStub.get().visibility, first.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.OFFERS,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_ID,
            adRequest = AsAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.OFFERS,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.DB_ERROR,
            adRequest = AsAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.OFFERS,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_TITLE,
            adRequest = AsAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
