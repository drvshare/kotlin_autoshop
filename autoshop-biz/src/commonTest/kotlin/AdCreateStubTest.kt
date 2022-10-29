package ru.drvshare.autoshop.biz.stub

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub

@OptIn(ExperimentalCoroutinesApi::class)
class AdCreateStubTest {

    private val processor = AsAdProcessor()
    private val id = AsAdId("666")
    private val title = "title 666"
    private val description = "Ищем автомобиль нива 1999 года с бензиновым двигателем объёмом 1.7л"
    private val releaseYear = LocalDate(1999, 1, 1)
    private val engineCapacity = "1.7"
    private val engineType = EAsEngineType.PETROL
    private val ownerId = AsUserId("user-1")
    private val dealSide = EAsDealSide.DEMAND
    private val visibility = EAsAdVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = AsAdContext(
            command = EAsCommand.CREATE,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.SUCCESS,
            adRequest = AsAd(
                id = id,
                title = title,
                description = description,
                releaseYear = releaseYear,
                engineCapacity = engineCapacity,
                engineType = engineType,
                ownerId = ownerId,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAdStub.get().id, ctx.adResponse.id)
        assertEquals(title, ctx.adResponse.title)
        assertEquals(description, ctx.adResponse.description)
        assertEquals(releaseYear, ctx.adResponse.releaseYear)
        assertEquals(engineCapacity, ctx.adResponse.engineCapacity)
        assertEquals(engineType, ctx.adResponse.engineType)
        assertEquals(ownerId, ctx.adResponse.ownerId)
        assertEquals(dealSide, ctx.adResponse.adType)
        assertEquals(visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.CREATE,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_TITLE,
            adRequest = AsAd(
                id = id,
                title = "",
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDescription() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.CREATE,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_DESCRIPTION,
            adRequest = AsAd(
                id = id,
                title = title,
                description = "",
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AsAdContext(
            command = EAsCommand.CREATE,
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
            command = EAsCommand.CREATE,
            state = EAsState.NONE,
            workMode = EAsWorkMode.STUB,
            stubCase = EAsAdStubs.BAD_ID,
            adRequest = AsAd(
                id = id,
                title = title,
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(AsAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
