package ru.drvshare.autoshop.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.app.module
import kotlin.test.assertEquals

class V1AdStubApiTest {

    @Test
    fun `test create`() = testApplication {
        //application(Application::module)

        val client = myClient()

        val response = client.post("/v1/ad/create") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<AdCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test read`() = testApplication {
        //application(Application::module)

        val client = myClient()

        val response = client.post("/v1/ad/read") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<AdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test update`() = testApplication {
        //application(Application::module)

        val client = myClient()

        val response = client.post("/v1/ad/update") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test delete`() = testApplication {
        //application(Application::module)

        val client = myClient()

        val response = client.post("/v1/ad/delete") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun `test search`() = testApplication {
        //application(Application::module)

        val client = myClient()

        val response = client.post("/v1/ad/search") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.ads?.first()?.id)
    }

    @Test
    fun `test offers`() = testApplication {
        //application(Application::module)

        val client = myClient()

        val response = client.post("/v1/ad/offers") {
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
