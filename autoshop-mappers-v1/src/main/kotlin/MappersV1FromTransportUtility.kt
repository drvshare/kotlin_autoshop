package ru.drvshare.autoshop.mappers.v1

import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs

internal fun ESteering?.fromTransport(): EAsSteering = when (this) {
    ESteering.LEFT_HAND_DRIVE -> EAsSteering.LEFT_HAND_DRIVE
    ESteering.RIGHT_HAND_DRIVE -> EAsSteering.RIGHT_HAND_DRIVE
    else -> EAsSteering.UNDEFINED
}

internal fun ETransmission?.fromTransport(): EAsTransmission = when (this) {
    ETransmission.MANUAL -> EAsTransmission.MANUAL
    ETransmission.AUTOMATIC -> EAsTransmission.AUTOMATIC
    ETransmission.ROBOT -> EAsTransmission.ROBOT
    else -> EAsTransmission.UNDEFINED
}

internal fun EEngineType?.fromTransport(): EAsEngineType = when (this) {
    EEngineType.PETROL -> EAsEngineType.PETROL
    EEngineType.DIESEL -> EAsEngineType.DIESEL
    EEngineType.ELECTRIC -> EAsEngineType.ELECTRIC
    else -> EAsEngineType.UNDEFINED
}

internal fun EAdVisibility?.fromTransport(): EAsAdVisibility = when (this) {
    EAdVisibility.PUBLIC -> EAsAdVisibility.VISIBLE_PUBLIC
    EAdVisibility.OWNER_ONLY -> EAsAdVisibility.VISIBLE_TO_OWNER
    EAdVisibility.REGISTERED_ONLY -> EAsAdVisibility.VISIBLE_TO_GROUP
    null -> EAsAdVisibility.NONE
}

internal fun EDealSide?.fromTransport(): EAsDealSide = when (this) {
    EDealSide.DEMAND -> EAsDealSide.DEMAND
    EDealSide.SUPPLY -> EAsDealSide.SUPPLY
    null -> EAsDealSide.NONE
}

internal fun String?.toAdId() = this?.let { AsAdId(it) } ?: AsAdId.NONE
internal fun String?.toAdWithId() = AsAd(id = this.toAdId())
internal fun IRequest?.requestId() = this?.requestId?.let { AsAdRequestId(it) } ?: AsAdRequestId.NONE
internal fun String?.toProductId() = this?.let { AsProductId(it) } ?: AsProductId.NONE

internal fun AdDebug?.transportToWorkMode(): EAsWorkMode = when (this?.mode) {
    EAdRequestDebugMode.PROD -> EAsWorkMode.PROD
    EAdRequestDebugMode.TEST -> EAsWorkMode.TEST
    EAdRequestDebugMode.STUB -> EAsWorkMode.STUB
    null -> EAsWorkMode.PROD
}

internal fun AdDebug?.transportToStubCase(): EAsAdStubs = when (this?.stub) {
    EAdRequestDebugStubs.SUCCESS -> EAsAdStubs.SUCCESS
    EAdRequestDebugStubs.NOT_FOUND -> EAsAdStubs.NOT_FOUND
    EAdRequestDebugStubs.BAD_ID -> EAsAdStubs.BAD_ID
    EAdRequestDebugStubs.BAD_TITLE -> EAsAdStubs.BAD_TITLE
    EAdRequestDebugStubs.BAD_DESCRIPTION -> EAsAdStubs.BAD_DESCRIPTION
    EAdRequestDebugStubs.BAD_VISIBILITY -> EAsAdStubs.BAD_VISIBILITY
    EAdRequestDebugStubs.CANNOT_DELETE -> EAsAdStubs.CANNOT_DELETE
    EAdRequestDebugStubs.BAD_SEARCH_STRING -> EAsAdStubs.BAD_SEARCH_STRING
    null -> EAsAdStubs.NONE
}

