package ru.drvshare.autoshop.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.drvshare.autoshop.api.v2.apiV2Mapper
import ru.drvshare.autoshop.api.v2.models.AdOffersRequest
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.mappers.v2.fromTransport
import ru.drvshare.autoshop.mappers.v2.toTransportOffers

suspend fun ApplicationCall.offersAd() {
    val request = apiV2Mapper.decodeFromString<AdOffersRequest>(receiveText())
    val context = AsAdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adsResponse.addAll(AutoShopAdStub.prepareOffersList("Нива", EAsDealSide.SUPPLY))
    respond(apiV2Mapper.encodeToString(context.toTransportOffers()))
}
