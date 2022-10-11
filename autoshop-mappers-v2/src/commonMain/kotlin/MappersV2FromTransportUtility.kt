package ru.drvshare.autoshop.mappers.v2

import ru.drvshare.autoshop.api.v2.models.*
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAdStubs


internal fun ESteering?.fromTransport(): EAutoSteering = when (this) {
    ESteering.LEFT_HAND_DRIVE -> EAutoSteering.LEFT_HAND_DRIVE
    ESteering.RIGHT_HAND_DRIVE -> EAutoSteering.RIGHT_HAND_DRIVE
    else -> EAutoSteering.UNDEFINED
}

internal fun ETransmission?.fromTransport(): EAutoTransmission = when (this) {
    ETransmission.MANUAL -> EAutoTransmission.MANUAL
    ETransmission.AUTOMATIC -> EAutoTransmission.AUTOMATIC
    ETransmission.ROBOT -> EAutoTransmission.ROBOT
    else -> EAutoTransmission.UNDEFINED
}

internal fun EEngineType?.fromTransport(): EAutoEngineType = when (this) {
    EEngineType.PETROL -> EAutoEngineType.PETROL
    EEngineType.DIESEL -> EAutoEngineType.DIESEL
    EEngineType.ELECTRIC -> EAutoEngineType.ELECTRIC
    else -> EAutoEngineType.UNDEFINED
}

internal fun EAdVisibility?.fromTransport(): EAutoShopAdVisibility = when (this) {
    EAdVisibility.PUBLIC -> EAutoShopAdVisibility.VISIBLE_PUBLIC
    EAdVisibility.OWNER_ONLY -> EAutoShopAdVisibility.VISIBLE_TO_OWNER
    EAdVisibility.REGISTERED_ONLY -> EAutoShopAdVisibility.VISIBLE_TO_GROUP
    null -> EAutoShopAdVisibility.NONE
}

internal fun EDealSide?.fromTransport(): EAutoShopDealSide = when (this) {
    EDealSide.DEMAND -> EAutoShopDealSide.DEMAND
    EDealSide.SUPPLY -> EAutoShopDealSide.SUPPLY
    null -> EAutoShopDealSide.NONE
}

internal fun String?.toAdId() = this?.let { AutoShopAdId(it) } ?: AutoShopAdId.NONE
internal fun String?.toAdWithId() = AutoShopAd(id = this.toAdId())
internal fun IRequest?.requestId() = this?.requestId?.let { AdRequestId(it) } ?: AdRequestId.NONE
internal fun String?.toProductId() = this?.let { AdProductId(it) } ?: AdProductId.NONE

internal fun AdDebug?.transportToWorkMode(): EAdWorkMode = when (this?.mode) {
    EAdRequestDebugMode.PROD -> EAdWorkMode.PROD
    EAdRequestDebugMode.TEST -> EAdWorkMode.TEST
    EAdRequestDebugMode.STUB -> EAdWorkMode.STUB
    null -> EAdWorkMode.PROD
}

internal fun AdDebug?.transportToStubCase(): EAdStubs = when (this?.stub) {
    EAdRequestDebugStubs.SUCCESS -> EAdStubs.SUCCESS
    EAdRequestDebugStubs.NOT_FOUND -> EAdStubs.NOT_FOUND
    EAdRequestDebugStubs.BAD_ID -> EAdStubs.BAD_ID
    EAdRequestDebugStubs.BAD_TITLE -> EAdStubs.BAD_TITLE
    EAdRequestDebugStubs.BAD_DESCRIPTION -> EAdStubs.BAD_DESCRIPTION
    EAdRequestDebugStubs.BAD_VISIBILITY -> EAdStubs.BAD_VISIBILITY
    EAdRequestDebugStubs.CANNOT_DELETE -> EAdStubs.CANNOT_DELETE
    EAdRequestDebugStubs.BAD_SEARCH_STRING -> EAdStubs.BAD_SEARCH_STRING
    null -> EAdStubs.NONE
}

