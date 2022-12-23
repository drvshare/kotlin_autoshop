package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.common.repo.DbAdFilterRequest
import ru.drvshare.autoshop.common.repo.DbAdsResponse
import ru.drvshare.autoshop.common.models.AsError


fun ICorAddExecDsl<AsAdContext>.repoOffers(title: String) = worker {
    this.title = title
    description = "Поиск предложений для объявления по названию"
    on { state == EAsState.RUNNING }
    handle {
        val adRequest = adRepoPrepare
        val filter = DbAdFilterRequest(
            titleFilter = adRequest.title,
            dealSide = when (adRequest.adType) {
                EAsDealSide.DEMAND -> EAsDealSide.SUPPLY
                EAsDealSide.SUPPLY -> EAsDealSide.DEMAND
                EAsDealSide.NONE -> EAsDealSide.NONE
            }
        )
        val dbResponse = if (filter.dealSide == EAsDealSide.NONE) {
            DbAdsResponse(
                data = null,
                isSuccess = false,
                errors = listOf(
                    AsError(
                        field = "adType",
                        message = "Type of ad must not be empty"
                    )
                )
            )
        } else {
            adRepo.searchAd(filter)
        }

        val resultAds = dbResponse.data
        when {
            !resultAds.isNullOrEmpty() -> adsRepoDone = resultAds.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = EAsState.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}
