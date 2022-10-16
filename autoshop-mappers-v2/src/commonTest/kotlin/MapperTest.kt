package ru.drvshare.autoshop.mappers.v2

import kotlinx.datetime.LocalDate
import ru.drvshare.autoshop.api.v2.models.*
import ru.drvshare.autoshop.common.AdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAdStubs
import kotlin.test.Test
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

        val context = AdContext()
        context.fromTransport(req)

        assertEquals(EAdStubs.SUCCESS, context.stubCase)
        assertEquals(EAdWorkMode.STUB, context.workMode)
        assertEquals("title", context.adRequest.title)
        assertEquals("desc", context.adRequest.description)
        assertEquals(LocalDate(1999, 1, 1), context.adRequest.releaseYear)
        assertEquals("25000", context.adRequest.odometer)
        assertEquals("1700", context.adRequest.engineCapacity)
        assertEquals(EAutoEngineType.DIESEL, context.adRequest.engineType)
        assertEquals(EAutoTransmission.MANUAL, context.adRequest.transmission)
        assertEquals(EAutoSteering.LEFT_HAND_DRIVE, context.adRequest.steering)

        assertEquals(EAutoShopAdVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
        assertEquals(EAutoShopDealSide.DEMAND, context.adRequest.adType)
    }

    @Test
    fun toTransport() {
        val context = AdContext(
            requestId = AdRequestId("1234"),
            command = EAdCommand.CREATE,
            adResponse = AutoShopAd(
                title = "title",
                description = "desc",
                releaseYear = LocalDate(1999, 1, 1),
                odometer = "25000",
                engineCapacity = "1700",
                engineType = EAutoEngineType.DIESEL,
                transmission = EAutoTransmission.MANUAL,
                steering = EAutoSteering.LEFT_HAND_DRIVE,
                adType = EAutoShopDealSide.DEMAND,
                visibility = EAutoShopAdVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                AdError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = EAdState.RUNNING,
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
