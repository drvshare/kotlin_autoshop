package ru.drvshare.autoshop.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.drvshare.autoshop.api.v2.models.AdOffersRequest
import ru.drvshare.autoshop.api.v2.models.AdOffersResponse
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.EAsCommand

suspend fun ApplicationCall.offersAd(processor: AsAdProcessor) =
    processV2<AdOffersRequest, AdOffersResponse>(processor, EAsCommand.OFFERS)
