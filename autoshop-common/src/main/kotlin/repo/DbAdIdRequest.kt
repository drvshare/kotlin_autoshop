package ru.drvshare.autoshop.common.repo

import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.AsAdId
import ru.drvshare.autoshop.common.models.AsAdLock

data class DbAdIdRequest(
    val id: AsAdId,
    val lock: AsAdLock = AsAdLock.NONE,
) {
    constructor(ad: AsAd): this(ad.id, ad.lock)
}
