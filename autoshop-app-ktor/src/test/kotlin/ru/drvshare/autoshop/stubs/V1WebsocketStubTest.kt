package ru.drvshare.autoshop.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import ru.drvshare.autoshop.api.v1.apiV1Mapper
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.app.module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WebsocketStubTest {

    @Test
    fun create() {
        testApplication {
            application(Application::module)
            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v1") {
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), IResponse::class.java)
                    assertIs<AdInitResponse>(response)
                }
                val requestObj = AdCreateRequest(
                    requestId = "12345",
                    ad = AdCreateObject(
                        title = "Болт",
                        description = "КРУТЕЙШИЙ",
                        adType = EDealSide.DEMAND,
                        visibility = EAdVisibility.PUBLIC,
                    ),
                    debug = AdDebug(
                        mode = EAdRequestDebugMode.STUB,
                        stub = EAdRequestDebugStubs.SUCCESS
                    )
                )
                send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), AdCreateResponse::class.java)

                    assertEquals("666", response.ad?.id)
                }
            }
        }
    }

    @Test
    fun read() {
        testApplication {
            application(Application::module)
            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v1") {
                withTimeout(3000) {
                    incoming.receive()
                }
                val requestObj = AdReadRequest(
                    requestId = "12345",
                    ad = AdReadObject("666"),
                    debug = AdDebug(
                        mode = EAdRequestDebugMode.STUB,
                        stub = EAdRequestDebugStubs.SUCCESS
                    )
                )
                send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), AdReadResponse::class.java)

                    assertEquals("666", response.ad?.id)
                }
            }
        }
    }

    @Test
    fun update() {
        testApplication {
            application(Application::module)
            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v1") {
                withTimeout(3000) {
                    incoming.receive()
                }
                val requestObj = AdUpdateRequest(
                    requestId = "12345",
                    ad = AdUpdateObject(
                        id = "666",
                        title = "Болт",
                        description = "КРУТЕЙШИЙ",
                        adType = EDealSide.DEMAND,
                        visibility = EAdVisibility.PUBLIC,
                    ),
                    debug = AdDebug(
                        mode = EAdRequestDebugMode.STUB,
                        stub = EAdRequestDebugStubs.SUCCESS
                    )
                )
                send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), AdUpdateResponse::class.java)

                    assertEquals("666", response.ad?.id)
                }
            }
        }
    }

    @Test
    fun delete() {
        testApplication {
            application(Application::module)
            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v1") {
                withTimeout(3000) {
                    incoming.receive()
                }
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
                send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), AdDeleteResponse::class.java)

                    assertEquals("666", response.ad?.id)
                }
            }
        }
    }

    @Test
    fun search() {
        testApplication {
            application(Application::module)
            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v1") {
                withTimeout(3000) {
                    incoming.receive()
                }
                val requestObj = AdSearchRequest(
                    requestId = "12345",
                    adFilter = AdSearchFilter(),
                    debug = AdDebug(
                        mode = EAdRequestDebugMode.STUB,
                        stub = EAdRequestDebugStubs.SUCCESS
                    )
                )
                send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), AdSearchResponse::class.java)

                    assertEquals("d-666-01", response.ads?.first()?.id)
                }
            }
        }
    }

    @Test
    fun offers() {
        testApplication {
            application(Application::module)
            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v1") {
                withTimeout(3000) {
                    incoming.receive()
                }
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
                send(Frame.Text(apiV1Mapper.writeValueAsString(requestObj)))
                withTimeout(3000) {
                    val incame = incoming.receive()
                    val response = apiV1Mapper.readValue((incame as Frame.Text).readText(), AdOffersResponse::class.java)

                    assertEquals("s-666-01", response.ads?.first()?.id)
                }
            }
        }
    }
}
