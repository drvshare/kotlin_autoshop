package ru.drvshare.autoshop.api.v1

import ru.drvshare.autoshop.api.v1.apiV1Mapper
import ru.drvshare.autoshop.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = AdCreateResponse(
        requestId = "123",
        ad = AdResponseObject(
            title = "ad title",
            description = "ad description",
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
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"ad title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"releaseYear\":\\s*1999"))
        assertContains(json, Regex("\"engineType\":\\s*\"diesel\""))
        assertContains(json, Regex("\"steering\":\\s*\"leftHandDrive\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as AdCreateResponse

        assertEquals(response, obj)
    }
}
