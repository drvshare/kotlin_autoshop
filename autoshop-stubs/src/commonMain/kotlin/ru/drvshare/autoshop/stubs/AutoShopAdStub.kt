package ru.drvshare.autoshop.stubs

import ru.drvshare.autoshop.common.models.*

object AutoShopAdStub {
    fun get() = AutoShopAd(
        id = AutoShopAdId("666"),
        title = "Требуется болт",
        description = "Требуется болт 100x5 с шестигранной шляпкой",
        ownerId = AdUserId("user-1"),
        adType = EAutoShopDealSide.DEMAND,
        visibility = EAutoShopAdVisibility.VISIBLE_PUBLIC,
        permissionsClient = mutableSetOf(
            EAutoShopAdPermissionClient.READ,
            EAutoShopAdPermissionClient.UPDATE,
            EAutoShopAdPermissionClient.DELETE,
            EAutoShopAdPermissionClient.MAKE_VISIBLE_PUBLIC,
            EAutoShopAdPermissionClient.MAKE_VISIBLE_GROUP,
            EAutoShopAdPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

    fun prepareResult(block: AutoShopAd.() -> Unit): AutoShopAd = get().apply(block)

    fun prepareSearchList(filter: String, type: EAutoShopDealSide) = listOf(
        adDemand("d-666-01", filter, type),
        adDemand("d-666-02", filter, type),
        adDemand("d-666-03", filter, type),
        adDemand("d-666-04", filter, type),
        adDemand("d-666-05", filter, type),
        adDemand("d-666-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: EAutoShopDealSide) = listOf(
        adSupply("s-666-01", filter, type),
        adSupply("s-666-02", filter, type),
        adSupply("s-666-03", filter, type),
        adSupply("s-666-04", filter, type),
        adSupply("s-666-05", filter, type),
        adSupply("s-666-06", filter, type),
    )

    private fun adDemand(id: String, filter: String, type: EAutoShopDealSide) =
        ad(get(), id = id, filter = filter, type = type)

    private fun adSupply(id: String, filter: String, type: EAutoShopDealSide) =
        ad(get(), id = id, filter = filter, type = type)

    private fun ad(base: AutoShopAd, id: String, filter: String, type: EAutoShopDealSide) = base.copy(
        id = AutoShopAdId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        adType = type,
    )

}
