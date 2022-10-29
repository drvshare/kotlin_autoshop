package ru.drvshare.autoshop.mappers.v1

import kotlinx.datetime.LocalDate
import org.junit.Test
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import kotlin.test.assertEquals


class MapperTest {
    @Test
    fun fromTransport() {
        val req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode = EAdRequestDebugMode.STUB,
                stub = EAdRequestDebugStubs.SUCCESS,
            ),
            ad = AdCreateObject(
                title = "title",
                description = "desc",
                releaseYear = 1999,
                odometer = "25000",
                engineCapacity = "1700",
                engineType = EEngineType.DIESEL,
                transmission = ETransmission.MANUAL,
                steering = ESteering.LEFT_HAND_DRIVE,

                adType = EDealSide.DEMAND,
                visibility = EAdVisibility.PUBLIC,
            ),
        )

        val context = AsAdContext()
        context.fromTransport(req)

        assertEquals(EAsAdStubs.SUCCESS, context.stubCase)
        assertEquals(EAsWorkMode.STUB, context.workMode)
        assertEquals("title", context.adRequest.title)
        assertEquals("desc", context.adRequest.description)
        assertEquals(LocalDate(1999, 1, 1), context.adRequest.releaseYear)
        assertEquals("25000", context.adRequest.odometer)
        assertEquals("1700", context.adRequest.engineCapacity)
        assertEquals(EAsEngineType.DIESEL, context.adRequest.engineType)
        assertEquals(EAsTransmission.MANUAL, context.adRequest.transmission)
        assertEquals(EAsSteering.LEFT_HAND_DRIVE, context.adRequest.steering)

        assertEquals(EAsAdVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
        assertEquals(EAsDealSide.DEMAND, context.adRequest.adType)
    }

    @Test
    fun toTransport() {
        val context = AsAdContext(
            requestId = AsAdRequestId("1234"),
            command = EAsCommand.CREATE,
            adResponse = AsAd(
                title = "title",
                description = "desc",
                releaseYear = LocalDate(1999, 1, 1),
                odometer = "25000",
                engineCapacity = "1700",
                engineType = EAsEngineType.DIESEL,
                transmission = EAsTransmission.MANUAL,
                steering = EAsSteering.LEFT_HAND_DRIVE,
                adType = EAsDealSide.DEMAND,
                visibility = EAsAdVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                AsError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = EAsState.RUNNING,
        )

        val req = context.toTransportAd() as AdCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.ad?.title)
        assertEquals("desc", req.ad?.description)
        assertEquals(1999, req.ad?.releaseYear)
        assertEquals("25000", req.ad?.odometer)
        assertEquals("1700", req.ad?.engineCapacity)
        assertEquals(EEngineType.DIESEL, req.ad?.engineType)
        assertEquals(ETransmission.MANUAL, req.ad?.transmission)
        assertEquals(ESteering.LEFT_HAND_DRIVE, req.ad?.steering)

        assertEquals(EAdVisibility.PUBLIC, req.ad?.visibility)
        assertEquals(EDealSide.DEMAND, req.ad?.adType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
