package ru.drvshare.autoshop.backend.repo.common

import ru.drvshare.autoshop.common.models.*

abstract class BaseInitAds(val op: String): IInitObjects<AsAd> {

    fun createInitTestModel(
        suf: String,
        ownerId: AsUserId = AsUserId("owner-123"),
        adType: EAsDealSide = EAsDealSide.DEMAND,
    ) = AsAd(
        id = AsAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = EAsAdVisibility.VISIBLE_TO_OWNER,
        adType = adType,
    )
}
