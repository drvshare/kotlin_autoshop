package ru.drvshare.autoshop.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.drvshare.autoshop.api.v2.models.*
import ru.drvshare.autoshop.mappers.v2.*
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.EAsCommand

suspend fun ApplicationCall.createAd(processor: AsAdProcessor) =
    processV2<AdCreateRequest, AdCreateResponse>(processor, EAsCommand.CREATE)

suspend fun ApplicationCall.readAd(processor: AsAdProcessor) =
    processV2<AdReadRequest, AdReadResponse>(processor, EAsCommand.READ)

suspend fun ApplicationCall.updateAd(processor: AsAdProcessor) =
    processV2<AdUpdateRequest, AdUpdateResponse>(processor, EAsCommand.UPDATE)

suspend fun ApplicationCall.deleteAd(processor: AsAdProcessor) =
    processV2<AdDeleteRequest, AdDeleteResponse>(processor, EAsCommand.DELETE)

suspend fun ApplicationCall.searchAd(processor: AsAdProcessor) =
    processV2<AdSearchRequest, AdSearchResponse>(processor, EAsCommand.SEARCH)
