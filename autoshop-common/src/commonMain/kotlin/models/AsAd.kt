package ru.drvshare.autoshop.common.models

import kotlinx.datetime.LocalDate


data class AsAd(
    var id: AsAdId = AsAdId.NONE,
    var title: String = "",
    var description: String = "",
    /** Год выпуска */
    val releaseYear: LocalDate? = null,
    /** Пробег */
    val odometer: String? = null,
    /** Объём двигателя */
    val engineCapacity: String? = null,
    /** Тип двигателя (Бензин/дизель/электро) */
    val engineType: EAsEngineType = EAsEngineType.UNDEFINED,
    /** Коробка передач (ручная, автомат, робот) */
    val transmission: EAsTransmission = EAsTransmission.UNDEFINED,
    /** Рулевое управление (Левый, Правый) */
    val steering: EAsSteering = EAsSteering.UNDEFINED,

    var ownerId: AsUserId = AsUserId.NONE,
    val adType: EAsDealSide = EAsDealSide.NONE,
    var visibility: EAsAdVisibility = EAsAdVisibility.NONE,
    var productId: AsProductId = AsProductId.NONE,
    val permissionsClient: MutableSet<EAsAdPermissionClient> = mutableSetOf()
)
