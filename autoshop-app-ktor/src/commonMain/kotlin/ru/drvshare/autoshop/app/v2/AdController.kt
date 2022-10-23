package ru.drvshare.autoshop.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.drvshare.autoshop.api.v2.apiV2Mapper
import ru.drvshare.autoshop.api.v2.models.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.mappers.v2.*
import ru.drvshare.autoshop.stubs.AutoShopAdStub

suspend fun ApplicationCall.createAd() {
    val request = apiV2Mapper.decodeFromString<AdCreateRequest>(receiveText())
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(apiV2Mapper.encodeToString(context.toTransportCreate()))
}

suspend fun ApplicationCall.readAd() {
    val request = apiV2Mapper.decodeFromString<AdReadRequest>(receiveText())
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(apiV2Mapper.encodeToString(context.toTransportRead()))
}

suspend fun ApplicationCall.updateAd() {
    val request = apiV2Mapper.decodeFromString<AdUpdateRequest>(receiveText())
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(apiV2Mapper.encodeToString(context.toTransportUpdate()))
}

suspend fun ApplicationCall.deleteAd() {
    val request = apiV2Mapper.decodeFromString<AdDeleteRequest>(receiveText())
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adResponse = AutoShopAdStub.get()
    respond(apiV2Mapper.encodeToString(context.toTransportDelete()))
}

suspend fun ApplicationCall.searchAd() {
    val request = apiV2Mapper.decodeFromString<AdSearchRequest>(receiveText())
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adsResponse.addAll(AutoShopAdStub.prepareSearchList("Нива", EAsDealSide.DEMAND))
    respond(apiV2Mapper.encodeToString(context.toTransportSearch()))
}
