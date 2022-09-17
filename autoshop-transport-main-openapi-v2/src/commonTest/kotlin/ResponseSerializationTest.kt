package ru.drvshare.autoshop.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.drvshare.autoshop.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response: IResponse = AdCreateResponse(
        responseType = "create",
        requestId = "123",
        ad = AdResponseObject(
            title = "ad title",
            description = "ad description",
            adType = EDealSide.DEMAND,
            visibility = EAdVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV2Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"ad title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(response)
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
//        val obj = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as AdCreateRequest
        val obj = apiV2Mapper.decodeFromString(json) as AdCreateResponse

        assertEquals(response, obj)
    }
}
