package ru.drvshare.autoshop.app.v1

import io.ktor.server.application.*
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.EAsCommand

suspend fun ApplicationCall.createAd(processor: AsAdProcessor) = processV1<AdCreateRequest, AdCreateResponse>(processor, EAsCommand.CREATE)

suspend fun ApplicationCall.readAd(processor: AsAdProcessor) = processV1<AdReadRequest, AdReadResponse>(processor, EAsCommand.READ)

suspend fun ApplicationCall.updateAd(processor: AsAdProcessor) = processV1<AdUpdateRequest, AdUpdateResponse>(processor, EAsCommand.UPDATE)

suspend fun ApplicationCall.deleteAd(processor: AsAdProcessor) = processV1<AdDeleteRequest, AdDeleteResponse>(processor, EAsCommand.DELETE)

suspend fun ApplicationCall.searchAd(processor: AsAdProcessor) = processV1<AdSearchRequest, AdSearchResponse>(processor, EAsCommand.SEARCH)
