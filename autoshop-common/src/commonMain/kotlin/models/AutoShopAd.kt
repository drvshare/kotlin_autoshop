package ru.drvshare.autoshop.common.models

data class AutoShopAd(
    var id: AutoShopAdId = AutoShopAdId.NONE,
    var title: String = "",
    var description: String = "",
    /** Год выпуска */
    val releaseYear: String? = null,
    /** Пробег */
    val odometer: String? = null,
    /** Объём двигателя */
    val engineCapacity: String? = null,
    /** Тип двигателя (Бензин/дизель/электро) */
    val engineType: EEngineType? = null,
    /** Коробка передач (ручная, автомат, робот) */
    val transmission: ETransmission? = null,
    /** Рулевое управление (Левый, Правый) */
    val steering: ESteering? = null,

    var ownerId: AdUserId = AdUserId.NONE,
    val adType: EAdDealSide = EAdDealSide.NONE,
    var visibility: EAdVisibility = EAdVisibility.NONE,
    var productId: AdProductId = AdProductId.NONE,
    val permissionsClient: MutableSet<EAutoShopAdPermissionClient> = mutableSetOf()
)
