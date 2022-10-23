package ru.drvshare.autoshop.stubs

import kotlinx.datetime.LocalDate
import ru.drvshare.autoshop.common.models.*

object ASAdStub {
    fun get() = AsAd(
        id = AsAdId("666"),
        title = "Ищем автомобиль Нива",
        description = "Ищем автомобиль нива 1999 года с бензиновым двигателем объёмом 1.7л",
        releaseYear = LocalDate(1999, 1, 1),
        engineCapacity = "1.7",
        engineType = EAsEngineType.PETROL,
        ownerId = AsUserId("user-1"),
        adType = EAsDealSide.DEMAND,
        visibility = EAsAdVisibility.VISIBLE_PUBLIC,
        permissionsClient = mutableSetOf(
            EAsAdPermissionClient.READ,
            EAsAdPermissionClient.UPDATE,
            EAsAdPermissionClient.DELETE,
            EAsAdPermissionClient.MAKE_VISIBLE_PUBLIC,
            EAsAdPermissionClient.MAKE_VISIBLE_GROUP,
            EAsAdPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

    fun prepareResult(block: AsAd.() -> Unit): AsAd = get().apply(block)

    fun prepareSearchList(filter: String, type: EAsDealSide) = listOf(
        adDemand("d-666-01", filter, type),
        adDemand("d-666-02", filter, type),
        adDemand("d-666-03", filter, type),
        adDemand("d-666-04", filter, type),
        adDemand("d-666-05", filter, type),
        adDemand("d-666-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: EAsDealSide) = listOf(
        adSupply("s-666-01", filter, type),
        adSupply("s-666-02", filter, type),
        adSupply("s-666-03", filter, type),
        adSupply("s-666-04", filter, type),
        adSupply("s-666-05", filter, type),
        adSupply("s-666-06", filter, type),
    )

    private fun adDemand(id: String, filter: String, type: EAsDealSide) =
        ad(get(), id = id, filter = filter, type = type)

    private fun adSupply(id: String, filter: String, type: EAsDealSide) =
        ad(get(), id = id, filter = filter, type = type)

    private fun ad(base: AsAd, id: String, filter: String, type: EAsDealSide) = base.copy(
        id = AsAdId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        adType = type,
    )

}
