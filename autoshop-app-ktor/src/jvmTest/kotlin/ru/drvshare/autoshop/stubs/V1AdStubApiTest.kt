package ru.drvshare.autoshop.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.drvshare.autoshop.api.v1.models.*
import kotlin.test.assertEquals

class V1AdStubApiTest {
    @Test
    fun `test create`() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/create") {
            val requestObj = AdCreateRequest(
                requestId = "1234",
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
                ),
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }

        val responseObject = response.body<AdCreateResponse>()
        println(responseObject)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("666", responseObject.ad?.id)
    }

    @Test
    fun `test read`() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/read") {
            val requestObj = AdReadRequest(
                requestId = "12345",
                ad = AdReadObject("666"),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test update`() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "1234",
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
                ),
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test delete`() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/delete") {
            val requestObj = AdDeleteRequest(
                requestId = "12345",
                ad = AdDeleteObject(
                    id = "666",
                ),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test search`() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/search") {
            val requestObj = AdSearchRequest(
                requestId = "12345",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.ads?.first()?.id)
    }

    @Test
    fun `test offers`() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/offers") {
            val requestObj = AdOffersRequest(
                requestId = "12345",
                ad = AdReadObject(
                    id = "666",
                ),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.STUB,
                    stub = EAdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdOffersResponse>()
        assertEquals(200, response.status.value)
        assertEquals("s-666-01", responseObj.ads?.first()?.id)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}
