package ru.drvshare.autoshop.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.drvshare.autoshop.biz.AsAdProcessor

fun Route.v1Ad(processor: AsAdProcessor) {
    route("ad") {
        post("create") {
            call.createAd(processor)
        }
        post("read") {
            call.readAd(processor)
        }
        post("update") {
            call.updateAd(processor)
        }
        post("delete") {
            call.deleteAd(processor)
        }
        post("search") {
            call.searchAd(processor)
        }
    }
}

fun Route.v1Offer(processor: AsAdProcessor) {
    route("ad") {
        post("offers") {
            call.offersAd(processor)
        }
    }
}
