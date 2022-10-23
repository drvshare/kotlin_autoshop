package ru.drvshare.autoshop.common.models

data class AsAdFilter(
    var searchString: String = "",
    var ownerId: AsUserId = AsUserId.NONE,
    var dealSide: EAsDealSide = EAsDealSide.NONE,
)
