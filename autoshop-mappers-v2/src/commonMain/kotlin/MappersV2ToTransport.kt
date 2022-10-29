package ru.drvshare.autoshop.mappers.v2

import ru.drvshare.autoshop.api.v2.models.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.mappers.v2.exceptions.UnknownAdCommand

fun AsAdContext.toTransportAd(): IResponse = when (val cmd = command) {
    EAsCommand.CREATE -> toTransportCreate()
    EAsCommand.READ -> toTransportRead()
    EAsCommand.UPDATE -> toTransportUpdate()
    EAsCommand.DELETE -> toTransportDelete()
    EAsCommand.SEARCH -> toTransportSearch()
    EAsCommand.OFFERS -> toTransportOffers()
    EAsCommand.NONE -> throw UnknownAdCommand(cmd)
}

fun AsAdContext.toTransportCreate() = AdCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportRead() = AdReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportUpdate() = AdUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportDelete() = AdDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportSearch() = AdSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun AsAdContext.toTransportOffers() = AdOffersResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)
