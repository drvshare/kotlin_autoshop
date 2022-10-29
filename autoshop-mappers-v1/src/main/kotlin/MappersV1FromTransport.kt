package ru.drvshare.autoshop.mappers.v1

import kotlinx.datetime.LocalDate
import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.AsAdFilter
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.mappers.v1.exceptions.UnknownRequestClass

fun AsAdContext.fromTransport(request: IRequest) = when (request) {
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    is AdOffersRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

fun AsAdContext.fromTransport(request: AdCreateRequest) {
    command = EAsCommand.CREATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: AsAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AsAdContext.fromTransport(request: AdReadRequest) {
    command = EAsCommand.READ
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AsAdContext.fromTransport(request: AdUpdateRequest) {
    command = EAsCommand.UPDATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: AsAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AsAdContext.fromTransport(request: AdDeleteRequest) {
    command = EAsCommand.DELETE
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AsAdContext.fromTransport(request: AdSearchRequest) {
    command = EAsCommand.SEARCH
    requestId = request.requestId()
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun AsAdContext.fromTransport(request: AdOffersRequest) {
    command = EAsCommand.OFFERS
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdSearchFilter?.toInternal(): AsAdFilter = AsAdFilter(
    searchString = this?.searchString ?: ""
)

private fun AdCreateObject.toInternal(): AsAd = AsAd(
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

private fun AdUpdateObject.toInternal(): AsAd = AsAd(
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


