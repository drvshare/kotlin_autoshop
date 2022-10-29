package ru.drvshare.autoshop.app

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.drvshare.autoshop.app.v2.v2Ad
import ru.drvshare.autoshop.app.v2.v2Offer
import ru.drvshare.autoshop.biz.AsAdProcessor

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    // Generally not needed as it is replaced by a `routing`
    install(Routing)

//    install(DefaultHeaders)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
//        allowCredentials = true
        anyHost() // TODO remove
    }

    val processor = AsAdProcessor()

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v2") {
            v2Ad(processor)
            v2Offer(processor)
        }
    }
}
