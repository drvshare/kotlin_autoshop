package ru.drvshare.autoshop.common

import kotlinx.datetime.Instant
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.repo.AsRepositories
import ru.drvshare.autoshop.common.repo.IAdRepository
import ru.drvshare.autoshop.common.stubs.EAsAdStubs

data class AsAdContext(
    var settings: AsSettings = AsSettings(),
    var command: EAsCommand = EAsCommand.NONE,
    var state: EAsState = EAsState.NONE,
    val errors: MutableList<AsError> = mutableListOf(),

    var workMode: EAsWorkMode = EAsWorkMode.PROD,
    var stubCase: EAsAdStubs = EAsAdStubs.NONE,

    var adRepos: AsRepositories = AsRepositories.NONE,
    var adRepo: IAdRepository = IAdRepository.NONE,

    var requestId: AsAdRequestId = AsAdRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: AsAd = AsAd(),
    var adFilterRequest: AsAdFilter = AsAdFilter(),

    var adValidating: AsAd = AsAd(),
    var adFilterValidating: AsAdFilter = AsAdFilter(),

    var adValidated: AsAd = AsAd(),
    var adFilterValidated: AsAdFilter = AsAdFilter(),

    var adRepoRead: AsAd = AsAd(),
    var adRepoPrepare: AsAd = AsAd(),
    var adRepoDone: AsAd = AsAd(),
    var adsRepoDone: MutableList<AsAd> = mutableListOf(),

    var adResponse: AsAd = AsAd(),
    var adsResponse: MutableList<AsAd> = mutableListOf(),
)
