package ru.drvshare.autoshop.app

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import ru.drvshare.autoshop.api.v1.apiV1Mapper
import ru.drvshare.autoshop.app.v1.v1Ad
import ru.drvshare.autoshop.app.v1.v1Offer
import ru.drvshare.autoshop.biz.AsAdProcessor

@Suppress("unused") // Referenced in application.conf
fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
//        val conf = YamlConfigLoader().load("./application.yaml")
//            ?: throw RuntimeException("Cannot read application.yaml")
//        println(conf)
//        config = conf
//        println("File read")

        module {
            module()
            moduleJvm()
        }
    }).apply {
        addShutdownHook {
            println("Stop is requested")
            stop(3000, 5000)
            println("Exiting")
        }
        start(true)
    }
}

fun Application.moduleJvm() {
    install(ContentNegotiation) {
        jackson {
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
    }
}
