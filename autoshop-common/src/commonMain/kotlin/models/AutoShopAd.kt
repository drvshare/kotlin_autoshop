package ru.drvshare.autoshop.common.models

import kotlinx.datetime.LocalDate


data class AutoShopAd(
    var id: AutoShopAdId = AutoShopAdId.NONE,
    var title: String = "",
    var description: String = "",
    /** Год выпуска */
    val releaseYear: LocalDate? = null,
    /** Пробег */
    val odometer: String? = null,
    /** Объём двигателя */
    val engineCapacity: String? = null,
    /** Тип двигателя (Бензин/дизель/электро) */
    val engineType: EAutoEngineType = EAutoEngineType.UNDEFINED,
    /** Коробка передач (ручная, автомат, робот) */
    val transmission: EAutoTransmission = EAutoTransmission.UNDEFINED,
    /** Рулевое управление (Левый, Правый) */
    val steering: EAutoSteering = EAutoSteering.UNDEFINED,

    var ownerId: AdUserId = AdUserId.NONE,
    val adType: EAutoShopDealSide = EAutoShopDealSide.NONE,
    var visibility: EAutoShopAdVisibility = EAutoShopAdVisibility.NONE,
    var productId: AdProductId = AdProductId.NONE,
    val permissionsClient: MutableSet<EAutoShopAdPermissionClient> = mutableSetOf()
)
