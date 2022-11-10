package ru.drvshare.autoshop.app.v1

import io.ktor.server.application.*
import ru.drvshare.autoshop.api.v1.models.AdOffersRequest
import ru.drvshare.autoshop.api.v1.models.AdOffersResponse
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.EAsCommand

suspend fun ApplicationCall.offersAd(processor: AsAdProcessor) =
    processV1<AdOffersRequest, AdOffersResponse>(processor, EAsCommand.OFFERS)
