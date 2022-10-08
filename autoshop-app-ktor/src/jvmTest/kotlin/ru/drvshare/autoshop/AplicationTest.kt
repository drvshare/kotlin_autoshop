package ru.drvshare.autoshop;

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AplicationTest {

    @Test
    fun `root endpoints`() = testApplication {
//        application(Application::module)
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, this.status)
            assertEquals("Hello, world!", this.bodyAsText())
        }
    }
}
