package ru.drvshare.autoshop.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.drvshare.autoshop.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = AdCreateRequest(
        requestType = "create",
        requestId = "123",
        debug = AdDebug(
            mode = EAdRequestDebugMode.STUB,
            stub = EAdRequestDebugStubs.BAD_TITLE
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
        )
    )

    @Test
    fun serialize() {
        val json = apiV2Mapper.encodeToString(request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"odometer\":\\s*\"25000\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
        val obj = apiV2Mapper.decodeFromString(json) as AdCreateRequest

        assertEquals(request, obj)
    }
}
