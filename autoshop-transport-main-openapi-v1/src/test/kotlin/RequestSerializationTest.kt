package ru.drvshare.autoshop.api.v1

import ru.drvshare.autoshop.api.apiV1Mapper
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.api.v1.models.AdCreateRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = AdCreateRequest(
        requestId = "123",
        debug = AdDebug(
            mode = EAdRequestDebugMode.STUB,
            stub = EAdRequestDebugStubs.BAD_TITLE
        ),
        ad = AdCreateObject(
            title = "ad title",
            description = "ad description",
            adType = EDealSide.DEMAND,
            visibility = EAdVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"ad title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as AdCreateRequest

        assertEquals(request, obj)
    }
}
