package ru.drvshare.autoshop.backend.repo.common

import ru.drvshare.autoshop.common.models.*

abstract class BaseInitAds(val op: String) : IInitObjects<AsAd> {

    open val lockOld: AsAdLock = AsAdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: AsAdLock = AsAdLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: AsUserId = AsUserId("owner-123"),
        adType: EAsDealSide = EAsDealSide.DEMAND,
        lock: AsAdLock = lockOld,
    ) = AsAd(
        id = AsAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = EAsAdVisibility.VISIBLE_TO_OWNER,
        adType = adType,
        lock = lock,
    )
}
