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

@OptIn(ExperimentalCoroutinesApi::class)
class AdDeleteStubTest {

    private val processor = AsAdProcessor()
    private val id = AsAdId("666")

    @Test
    fun delete() = runTest {

        val ctx = AsAdContext(
            command = EAsCommand.DELETE,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.SUCCESS,
            adRequest = AsAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = AsAdStub.get()
        assertEquals(stub.id, ctx.adResponse.id)
        assertEquals(stub.title, ctx.adResponse.title)
        assertEquals(stub.description, ctx.adResponse.description)
        assertEquals(stub.adType, ctx.adResponse.adType)
        assertEquals(stub.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.DELETE,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_ID,
            adRequest = AsAd(),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.DELETE,
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
            command = EAsCommand.DELETE,
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
