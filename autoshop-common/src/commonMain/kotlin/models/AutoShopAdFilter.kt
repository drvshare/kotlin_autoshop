package ru.drvshare.autoshop.common.models

data class AutoShopAdFilter(
    var searchString: String = "",
    var ownerId: AdUserId = AdUserId.NONE,
    var dealSide: EAdDealSide = EAdDealSide.NONE,
)
