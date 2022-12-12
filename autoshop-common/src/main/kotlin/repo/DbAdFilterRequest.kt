package ru.drvshare.autoshop.common.repo

import ru.drvshare.autoshop.common.models.AsUserId
import ru.drvshare.autoshop.common.models.EAsDealSide

data class DbAdFilterRequest(
    val titleFilter: String = "",
    val ownerId: AsUserId = AsUserId.NONE,
    val dealSide: EAsDealSide = EAsDealSide.NONE,
)
