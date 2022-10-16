package ru.drvshare.autoshop.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AdContext
import ru.drvshare.autoshop.common.models.EAutoShopDealSide
import ru.drvshare.autoshop.mappers.v1.*
import ru.drvshare.autoshop.stubs.AutoShopAdStub

suspend fun ApplicationCall.createAd() {
    val request = receive<AdCreateRequest>()
    val context = AdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readAd() {
    val request = receive<AdReadRequest>()
    val context = AdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAd() {
    val request = receive<AdUpdateRequest>()
    val context = AdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteAd() {
    val request = receive<AdDeleteRequest>()
    val context = AdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchAd() {
    val request = receive<AdSearchRequest>()
    val context = AdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adsResponse.addAll(AutoShopAdStub.prepareSearchList("Нива", EAutoShopDealSide.DEMAND))
    respond(context.toTransportSearch())
}
