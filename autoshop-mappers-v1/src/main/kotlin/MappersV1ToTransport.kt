package ru.drvshare.autoshop.mappers.v1

import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.mappers.v1.exceptions.UnknownAdCommand

fun AdContext.toTransportAd(): IResponse = when (val cmd = command) {
    EAdCommand.CREATE -> toTransportCreate()
    EAdCommand.READ -> toTransportRead()
    EAdCommand.UPDATE -> toTransportUpdate()
    EAdCommand.DELETE -> toTransportDelete()
    EAdCommand.SEARCH -> toTransportSearch()
    EAdCommand.OFFERS -> toTransportOffers()
    EAdCommand.NONE -> throw UnknownAdCommand(cmd)
}

fun AdContext.toTransportCreate() = AdCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAdState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AdContext.toTransportRead() = AdReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAdState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AdContext.toTransportUpdate() = AdUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAdState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AdContext.toTransportDelete() = AdDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAdState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun AdContext.toTransportSearch() = AdSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAdState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun AdContext.toTransportOffers() = AdOffersResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EAdState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

