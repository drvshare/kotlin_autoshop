package ru.drvshare.autoshop.app

import com.fasterxml.jackson.databind.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*
//import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.drvshare.autoshop.api.v1.apiV1Mapper
import ru.drvshare.autoshop.app.base.KtorUserSession
import ru.drvshare.autoshop.app.base.KtorWsSessions
import ru.drvshare.autoshop.app.v1.asWsHandlerV1
import ru.drvshare.autoshop.app.v1.v1Ad
import ru.drvshare.autoshop.app.v1.v1Offer
import ru.drvshare.autoshop.biz.AsAdProcessor

@Suppress("unused") // Referenced in application.conf
fun main() {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        val conf = YamlConfigLoader().load("./application.yaml")
            ?: throw RuntimeException("Cannot read application.yaml")
        println(conf)
        config = conf
        println("File read")

        module {
            module()
        }
        connector {
            port = conf.tryGetString("ktor.deployment.port")?.toIntOrNull() ?: 8080
            host = conf.tryGetString("ktor.deployment.host") ?: "0.0.0.0"
        }
    }).apply {
//        addShutdownHook {
//            println("Stop is requested")
//            stop(3000, 5000)
//            println("Exiting")
//        }
        start(true)
    }
}

fun Application.module() {
    // Generally not needed as it is replaced by a `routing`
    install(Routing)
    install(WebSockets)

//    install(DefaultHeaders)

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }

    install(CallLogging) {
        level = Level.INFO
    }
    val processor = AsAdProcessor()
    routing {
        route("v1") {
            v1Ad(processor)
            v1Offer(processor)
        }

        webSocket("/ws/v1") {
            asWsHandlerV1(processor, KtorWsSessions.sessions)
        }


    }
}
