package ru.drvshare.autoshop.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.mappers.v1.*
import ru.drvshare.autoshop.stubs.AsAdStub

suspend fun ApplicationCall.createAd() {
    val request = receive<AdCreateRequest>()
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AsAdStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readAd() {
    val request = receive<AdReadRequest>()
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AsAdStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAd() {
    val request = receive<AdUpdateRequest>()
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AsAdStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteAd() {
    val request = receive<AdDeleteRequest>()
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AsAdStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchAd() {
    val request = receive<AdSearchRequest>()
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adsResponse.addAll(AsAdStub.prepareSearchList("Нива", EAsDealSide.DEMAND))
    respond(context.toTransportSearch())
}
