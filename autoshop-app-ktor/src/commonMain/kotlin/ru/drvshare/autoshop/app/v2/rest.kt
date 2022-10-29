package ru.drvshare.autoshop.app.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.drvshare.autoshop.biz.AsAdProcessor

fun Route.v2Ad(processor: AsAdProcessor) {
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

fun Route.v2Offer(processor: AsAdProcessor) {
    route("ad") {
        post("offers") {
            call.offersAd(processor)
        }
    }
}
