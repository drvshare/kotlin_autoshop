package ru.drvshare.autoshop.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import ru.drvshare.autoshop.api.v2.apiV2Mapper
import ru.drvshare.autoshop.api.v2.models.*
import kotlin.test.assertEquals

class V2AdStubApiTest {

    @Test
    fun `test create`() = testApplication {
        val response = client.post("/v2/ad/create") {
            val requestObj = AdCreateRequest(
                requestType = "create",
                requestId = "12345",
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
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test read`() = testApplication {
        val response = client.post("/v2/ad/read") {
            val requestObj = AdReadRequest(
                requestType = "read",
                requestId = "12345",
                ad = AdReadObject("666"),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test update`() = testApplication {
        val response = client.post("/v2/ad/update") {
            val requestObj = AdUpdateRequest(
                requestType = "update",
                requestId = "12345",
                ad = AdUpdateObject(
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
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test delete`() = testApplication {
        val response = client.post("/v2/ad/delete") {
            val requestObj = AdDeleteRequest(
                requestType = "delete",
                requestId = "12345",
                ad = AdDeleteObject(
                    id = "666",
                    lock = "123"
                ),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test search`() = testApplication {
        val response = client.post("/v2/ad/search") {
            val requestObj = AdSearchRequest(
                requestType = "search",
                requestId = "12345",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.ads?.first()?.id)
    }

    @Test
    fun `test offers`() = testApplication {
        val response = client.post("/v2/ad/offers") {
            val requestObj = AdOffersRequest(
                requestType = "offers",
                requestId = "12345",
                ad = AdReadObject(
                    id = "666"
                ),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<AdOffersResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("s-666-01", responseObj.ads?.first()?.id)
    }
}
