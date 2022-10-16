package ru.drvshare.autoshop.mappers.v2

import ru.drvshare.autoshop.api.v2.models.*
import ru.drvshare.autoshop.common.models.*

internal fun List<AutoShopAd>.toTransportAd(): List<AdResponseObject>? = this.map { it.toTransportAd() }.toList().takeIf { it.isNotEmpty() }

internal fun AutoShopAd.toTransportAd(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != AutoShopAdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    releaseYear = releaseYear?.year,
    odometer = odometer.takeIf { it?.isNotBlank() == true },
    engineCapacity = engineCapacity.takeIf { it?.isNotBlank() == true },
    engineType = engineType.toTransportAd(),
    transmission = transmission.toTransportAd(),
    steering = steering.toTransportAd(),
    ownerId = ownerId.takeIf { it != AdUserId.NONE }?.asString(),
    adType = adType.toTransportAd(),
    visibility = visibility.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
    productId = productId.takeIf { it != AdProductId.NONE }?.asString(),
)

private fun EAutoSteering.toTransportAd(): ESteering = when (this) {
    EAutoSteering.LEFT_HAND_DRIVE -> ESteering.LEFT_HAND_DRIVE
    EAutoSteering.RIGHT_HAND_DRIVE -> ESteering.RIGHT_HAND_DRIVE
    else -> ESteering.UNDEFINED
}

private fun EAutoEngineType.toTransportAd() = when (this) {
    EAutoEngineType.DIESEL -> EEngineType.DIESEL
    EAutoEngineType.ELECTRIC -> EEngineType.ELECTRIC
    EAutoEngineType.PETROL -> EEngineType.PETROL
    else -> EEngineType.UNDEFINED
}

private fun EAutoTransmission.toTransportAd() = when (this) {
    EAutoTransmission.MANUAL -> ETransmission.MANUAL
    EAutoTransmission.AUTOMATIC -> ETransmission.AUTOMATIC
    EAutoTransmission.ROBOT -> ETransmission.ROBOT
    else -> ETransmission.UNDEFINED
}

private fun Set<EAutoShopAdPermissionClient>.toTransportAd(): Set<EAdPermissions>? = this.map { it.toTransportAd() }.toSet().takeIf { it.isNotEmpty() }

private fun EAutoShopAdPermissionClient.toTransportAd() = when (this) {
    EAutoShopAdPermissionClient.READ -> EAdPermissions.READ
    EAutoShopAdPermissionClient.UPDATE -> EAdPermissions.UPDATE
    EAutoShopAdPermissionClient.MAKE_VISIBLE_OWNER -> EAdPermissions.MAKE_VISIBLE_OWN
    EAutoShopAdPermissionClient.MAKE_VISIBLE_GROUP -> EAdPermissions.MAKE_VISIBLE_GROUP
    EAutoShopAdPermissionClient.MAKE_VISIBLE_PUBLIC -> EAdPermissions.MAKE_VISIBLE_PUBLIC
    EAutoShopAdPermissionClient.DELETE -> EAdPermissions.DELETE
}

private fun EAutoShopAdVisibility.toTransportAd(): EAdVisibility? = when (this) {
    EAutoShopAdVisibility.VISIBLE_PUBLIC -> EAdVisibility.PUBLIC
    EAutoShopAdVisibility.VISIBLE_TO_GROUP -> EAdVisibility.REGISTERED_ONLY
    EAutoShopAdVisibility.VISIBLE_TO_OWNER -> EAdVisibility.OWNER_ONLY
    EAutoShopAdVisibility.NONE -> null
}

private fun EAutoShopDealSide.toTransportAd(): EDealSide? = when (this) {
    EAutoShopDealSide.DEMAND -> EDealSide.DEMAND
    EAutoShopDealSide.SUPPLY -> EDealSide.SUPPLY
    EAutoShopDealSide.NONE -> null
}

internal fun List<AdError>.toTransportErrors(): List<Error>? = this.map { it.toTransportAd() }.toList().takeIf { it.isNotEmpty() }

private fun AdError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
