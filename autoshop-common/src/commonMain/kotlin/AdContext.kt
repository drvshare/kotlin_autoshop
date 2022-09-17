package ru.drvshare.autoshop.common

import kotlinx.datetime.Instant
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.AdStubs

data class AdContext(
    var command: EAdCommand = EAdCommand.NONE,
    var state: EAdState = EAdState.NONE,
    val errors: MutableList<AdError> = mutableListOf(),

    var workMode: EAdWorkMode = EAdWorkMode.PROD,
    var stubCase: AdStubs = AdStubs.NONE,

    var requestId: AdRequestId = AdRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: AutoShopAd = AutoShopAd(),
    var adFilterRequest: AutoShopAdFilter = AutoShopAdFilter(),
    var adResponse: AutoShopAd = AutoShopAd(),
    var adsResponse: MutableList<AutoShopAd> = mutableListOf(),
)
