package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.repo.DbAdIdRequest
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == EAsState.RUNNING }
    handle {
        val request = DbAdIdRequest(adValidated)
        val result = adRepo.readAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoRead = resultAd
        } else {
            state = EAsState.FAILING
            errors.addAll(result.errors)
        }
    }
}
