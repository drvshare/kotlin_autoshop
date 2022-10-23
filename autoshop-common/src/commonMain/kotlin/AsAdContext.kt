package ru.drvshare.autoshop.common

import kotlinx.datetime.Instant
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs

data class AsAdContext(
    var command: EAsCommand = EAsCommand.NONE,
    var state: EAsState = EAsState.NONE,
    val errors: MutableList<AsError> = mutableListOf(),

    var workMode: EAsWorkMode = EAsWorkMode.PROD,
    var stubCase: EAsAdStubs = EAsAdStubs.NONE,

    var requestId: AsAdRequestId = AsAdRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: AsAd = AsAd(),
    var adFilterRequest: AsAdFilter = AsAdFilter(),
    var adResponse: AsAd = AsAd(),
    var adsResponse: MutableList<AsAd> = mutableListOf(),
)
