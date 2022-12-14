package ru.drvshare.autoshop.mappers.v1

import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.mappers.v1.exceptions.UnknownAdCommand

fun AsAdContext.toTransportAd(): IResponse = when (val cmd = command) {
    EAsCommand.CREATE -> toTransportCreate()
    EAsCommand.READ -> toTransportRead()
    EAsCommand.UPDATE -> toTransportUpdate()
    EAsCommand.DELETE -> toTransportDelete()
    EAsCommand.SEARCH -> toTransportSearch()
    EAsCommand.OFFERS -> toTransportOffers()
    EAsCommand.NONE -> throw UnknownAdCommand(cmd)
}

fun AsAdContext.toTransportInit() = AdInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

fun AsAdContext.toTransportCreate() = AdCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.FINISHING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportRead() = AdReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.FINISHING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportUpdate() = AdUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.FINISHING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportDelete() = AdDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.FINISHING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AsAdContext.toTransportSearch() = AdSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.FINISHING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun AsAdContext.toTransportOffers() = AdOffersResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAsState.FINISHING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)
