package ru.drvshare.autoshop.ktor.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.app.module
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoInMemory
import ru.drvshare.autoshop.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Suppress("TestMethodWithoutAssertion")
class V1AdInmemoryApiTest {
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidSup = "10000000-0000-0000-0000-000000000003"
    private val initAd = AsAd(
        id = AsAdId(uuidOld),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.DEMAND,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        lock = AsAdLock(uuidOld),
    )
    private val initAdSupply = AsAd(
        id = AsAdId(uuidSup),
        title = "abc",
        description = "abc",
        adType = EAsDealSide.SUPPLY,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        lock = AsAdLock(uuidSup),
    )

    @Test
    fun create() = testApplication {
        application {
            val repo by lazy {
                AdRepoInMemory(randomUuid = { uuidNew })
            }
            val settigs by lazy { AsSettings(repoTest = repo) }
            module(settigs)
        }
        val client = myClient()

        val createAd = AdCreateObject(
            title = "Болт",
            description = "КРУТЕЙШИЙ",
            adType = EDealSide.DEMAND,
            visibility = EAdVisibility.PUBLIC,
        )

        val response = client.post("/v1/ad/create") {
            val requestObj = AdCreateRequest(
                requestId = "12345",
                ad = createAd,
                debug = AdDebug(
                    mode = EAdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidNew, responseObj.ad?.id)
        assertEquals(createAd.title, responseObj.ad?.title)
        assertEquals(createAd.description, responseObj.ad?.description)
        assertEquals(createAd.adType, responseObj.ad?.adType)
        assertEquals(createAd.visibility, responseObj.ad?.visibility)
    }

    @Test
    fun read() = testApplication {
        application {
            val repo by lazy {
                AdRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
            }
            val settigs by lazy { AsSettings(repoTest = repo) }
            module(settigs)
        }
        val client = myClient()

        val response = client.post("/v1/ad/read") {
            val requestObj = AdReadRequest(
                requestId = "12345",
                ad = AdReadObject(uuidOld),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.ad?.id)
    }

    @Test
    fun update() = testApplication {
        application {
            val repo by lazy {
                AdRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
            }
            val settigs by lazy { AsSettings(repoTest = repo) }
            module(settigs)
        }
        val client = myClient()

        val adUpdate = AdUpdateObject(
            id = uuidOld,
            title = "Болт",
            description = "КРУТЕЙШИЙ",
            adType = EDealSide.DEMAND,
            visibility = EAdVisibility.PUBLIC,
            lock = initAd.lock.asString()

        )

        val response = client.post("/v1/ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "12345",
                ad = adUpdate,
                debug = AdDebug(
                    mode = EAdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(adUpdate.id, responseObj.ad?.id)
        assertEquals(adUpdate.title, responseObj.ad?.title)
        assertEquals(adUpdate.description, responseObj.ad?.description)
        assertEquals(adUpdate.adType, responseObj.ad?.adType)
        assertEquals(adUpdate.visibility, responseObj.ad?.visibility)
    }

    @Test
    fun delete() = testApplication {
        application {
            val repo by lazy {
                AdRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
            }
            val settigs by lazy { AsSettings(repoTest = repo) }
            module(settigs)
        }
        val client = myClient()

        val response = client.post("/v1/ad/delete") {
            val requestObj = AdDeleteRequest(
                requestId = "12345",
                ad = AdDeleteObject(
                    id = uuidOld,
                    lock = initAd.lock.asString()

                ),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.ad?.id)
    }

    @Test
    fun search() = testApplication {
        application {
            val repo by lazy {
                AdRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
            }
            val settigs by lazy { AsSettings(repoTest = repo) }
            module(settigs)
        }
        val client = myClient()

        val response = client.post("/v1/ad/search") {
            val requestObj = AdSearchRequest(
                requestId = "12345",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.ads?.size)
        assertEquals(uuidOld, responseObj.ads?.first()?.id)
    }

    @Test
    fun offers() = testApplication {
        application {
            val repo by lazy {
                AdRepoInMemory(initObjects = listOf(initAd, initAdSupply), randomUuid = { uuidNew })
            }
            val settigs by lazy { AsSettings(repoTest = repo) }
            module(settigs)
        }
        val client = myClient()

        val response = client.post("/v1/ad/offers") {
            val requestObj = AdOffersRequest(
                requestId = "12345",
                ad = AdReadObject(
                    id = uuidOld,
                ),
                debug = AdDebug(
                    mode = EAdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdOffersResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.ads?.size)
        assertEquals(uuidSup, responseObj.ads?.first()?.id)
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
