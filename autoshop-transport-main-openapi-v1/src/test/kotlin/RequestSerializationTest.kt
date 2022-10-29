package ru.drvshare.autoshop.api.v1

import ru.drvshare.autoshop.api.v1.apiV1Mapper
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.api.v1.models.AdCreateRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = AdCreateRequest(
        requestId = "1234",
        debug = AdDebug(
            mode = EAdRequestDebugMode.STUB,
            stub = EAdRequestDebugStubs.BAD_TITLE,
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

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"releaseYear\":\\s*1999"))
        assertContains(json, Regex("\"engineType\":\\s*\"diesel\""))
        assertContains(json, Regex("\"steering\":\\s*\"leftHandDrive\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as AdCreateRequest

        assertEquals(request, obj)
    }
}
