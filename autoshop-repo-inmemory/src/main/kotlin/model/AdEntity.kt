package ru.drvshare.autoshop.backend.repository.inmemory.model

import ru.drvshare.autoshop.common.models.*

data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val adType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: AsAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        adType = model.adType.takeIf { it != EAsDealSide.NONE }?.name,
        visibility = model.visibility.takeIf { it != EAsAdVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = AsAd(
        id = id?.let { AsAdId(it) }?: AsAdId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { AsUserId(it) }?: AsUserId.NONE,
        adType = adType?.let { EAsDealSide.valueOf(it) }?: EAsDealSide.NONE,
        visibility = visibility?.let { EAsAdVisibility.valueOf(it) }?: EAsAdVisibility.NONE,
        lock = lock?.let { AsAdLock(it) } ?: AsAdLock.NONE,
    )
}
