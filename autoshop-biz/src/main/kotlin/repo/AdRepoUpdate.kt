package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.repo.DbAdRequest
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker

fun ICorAddExecDsl<AsAdContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == EAsState.RUNNING }
    handle {
        val request = DbAdRequest(
            adRepoPrepare.deepCopy().apply {
                this.title = adValidated.title
                description = adValidated.description
                adType = adValidated.adType
                visibility = adValidated.visibility
            }
        )
        val result = adRepo.updateAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = EAsState.FAILING
            errors.addAll(result.errors)
            adRepoDone
        }
    }
}
