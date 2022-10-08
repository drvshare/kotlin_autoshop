package ru.drvshare.autoshop.mappers.v1

import kotlinx.datetime.LocalDate
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AdContext
import ru.drvshare.autoshop.common.models.AutoShopAd
import ru.drvshare.autoshop.common.models.AutoShopAdFilter
import ru.drvshare.autoshop.common.models.EAdCommand
import ru.drvshare.autoshop.mappers.v1.exceptions.UnknownRequestClass

fun AdContext.fromTransport(request: IRequest) = when (request) {
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    is AdOffersRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

fun AdContext.fromTransport(request: AdCreateRequest) {
    command = EAdCommand.CREATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: AutoShopAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AdContext.fromTransport(request: AdReadRequest) {
    command = EAdCommand.READ
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AdContext.fromTransport(request: AdUpdateRequest) {
    command = EAdCommand.UPDATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: AutoShopAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AdContext.fromTransport(request: AdDeleteRequest) {
    command = EAdCommand.DELETE
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AdContext.fromTransport(request: AdSearchRequest) {
    command = EAdCommand.SEARCH
    requestId = request.requestId()
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AdContext.fromTransport(request: AdOffersRequest) {
    command = EAdCommand.OFFERS
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdSearchFilter?.toInternal(): AutoShopAdFilter = AutoShopAdFilter(
    searchString = this?.searchString ?: ""
)

private fun AdCreateObject.toInternal(): AutoShopAd = AutoShopAd(
    title = this.title ?: "",
    description = this.description ?: "",
    releaseYear = releaseYear?.let { LocalDate(it, 1, 1) },
    odometer = this.odometer ?: "",
    engineCapacity = this.engineCapacity ?: "",
    engineType = this.engineType.fromTransport(),
    transmission = this.transmission.fromTransport(),
    steering = this.steering.fromTransport(),

    adType = this.adType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun AdUpdateObject.toInternal(): AutoShopAd = AutoShopAd(
    id = this.id.toAdId(),
    title = this.title ?: "",
    description = this.description ?: "",
    releaseYear = releaseYear?.let { LocalDate(it, 1, 1) },
    odometer = this.odometer ?: "",
    engineCapacity = this.engineCapacity ?: "",
    engineType = this.engineType.fromTransport(),
    transmission = this.transmission.fromTransport(),
    steering = this.steering.fromTransport(),

    adType = this.adType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)


