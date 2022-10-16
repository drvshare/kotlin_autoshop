package ru.drvshare.autoshop.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.drvshare.autoshop.api.v1.models.AdOffersRequest
import ru.drvshare.autoshop.common.AdContext
import ru.drvshare.autoshop.common.models.EAutoShopDealSide
import ru.drvshare.autoshop.mappers.v1.fromTransport
import ru.drvshare.autoshop.mappers.v1.toTransportOffers
import ru.drvshare.autoshop.stubs.AutoShopAdStub

suspend fun ApplicationCall.offersAd() {
    val request = receive<AdOffersRequest>()
    val context = AdContext()
    context.fromTransport(request)
    /** TODO! Пока нет бизнес-логики используем stub */
    context.adsResponse.addAll(AutoShopAdStub.prepareOffersList("Нива", EAutoShopDealSide.SUPPLY))
    respond(context.toTransportOffers())
}
