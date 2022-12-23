package ru.drvshare.autoshop.mappers.v1

import ru.drvshare.autoshop.api.v1.models.*
import ru.drvshare.autoshop.common.models.*

internal fun List<AsAd>.toTransportAd(): List<AdResponseObject>? = this.map { it.toTransportAd() }.toList().takeIf { it.isNotEmpty() }

internal fun AsAd.toTransportAd(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != AsAdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    releaseYear = releaseYear?.year,
    odometer = odometer.takeIf { it?.isNotBlank() == true },
    engineCapacity = engineCapacity.takeIf { it?.isNotBlank() == true },
    engineType = engineType.toTransportAd(),
    transmission = transmission.toTransportAd(),
    steering = steering.toTransportAd(),
    ownerId = ownerId.takeIf { it != AsUserId.NONE }?.asString(),
    adType = adType.toTransportAd(),
    visibility = visibility.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
    lock = lock.takeIf { it != AsAdLock.NONE }?.asString()
)

private fun EAsSteering.toTransportAd(): ESteering = when (this) {
    EAsSteering.LEFT_HAND_DRIVE -> ESteering.LEFT_HAND_DRIVE
    EAsSteering.RIGHT_HAND_DRIVE -> ESteering.RIGHT_HAND_DRIVE
    else -> ESteering.UNDEFINED
}

private fun EAsEngineType.toTransportAd() = when (this) {
    EAsEngineType.DIESEL -> EEngineType.DIESEL
    EAsEngineType.ELECTRIC -> EEngineType.ELECTRIC
    EAsEngineType.PETROL -> EEngineType.PETROL
    else -> EEngineType.UNDEFINED
}

private fun EAsTransmission.toTransportAd() = when (this) {
    EAsTransmission.MANUAL -> ETransmission.MANUAL
    EAsTransmission.AUTOMATIC -> ETransmission.AUTOMATIC
    EAsTransmission.ROBOT -> ETransmission.ROBOT
    else -> ETransmission.UNDEFINED
}

private fun Set<EAsAdPermissionClient>.toTransportAd(): Set<EAdPermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun EAsAdPermissionClient.toTransportAd() = when (this) {
    EAsAdPermissionClient.READ -> EAdPermissions.READ
    EAsAdPermissionClient.UPDATE -> EAdPermissions.UPDATE
    EAsAdPermissionClient.MAKE_VISIBLE_OWNER -> EAdPermissions.MAKE_VISIBLE_OWN
    EAsAdPermissionClient.MAKE_VISIBLE_GROUP -> EAdPermissions.MAKE_VISIBLE_GROUP
    EAsAdPermissionClient.MAKE_VISIBLE_PUBLIC -> EAdPermissions.MAKE_VISIBLE_PUBLIC
    EAsAdPermissionClient.DELETE -> EAdPermissions.DELETE
}

private fun EAsAdVisibility.toTransportAd(): EAdVisibility? = when (this) {
    EAsAdVisibility.VISIBLE_PUBLIC -> EAdVisibility.PUBLIC
    EAsAdVisibility.VISIBLE_TO_GROUP -> EAdVisibility.REGISTERED_ONLY
    EAsAdVisibility.VISIBLE_TO_OWNER -> EAdVisibility.OWNER_ONLY
    EAsAdVisibility.NONE -> null
}

private fun EAsDealSide.toTransportAd(): EDealSide? = when (this) {
    EAsDealSide.DEMAND -> EDealSide.DEMAND
    EAsDealSide.SUPPLY -> EDealSide.SUPPLY
    EAsDealSide.NONE -> null
}

internal fun List<AsError>.toTransportErrors(): List<Error>? = this.map { it.toTransportAd() }.toList().takeIf { it.isNotEmpty() }

private fun AsError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
