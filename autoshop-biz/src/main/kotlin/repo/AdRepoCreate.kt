package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.repo.DbAdRequest
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker

fun ICorAddExecDsl<AsAdContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == EAsState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        val result = adRepo.createAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = EAsState.FAILING
            errors.addAll(result.errors)
        }
    }
}
