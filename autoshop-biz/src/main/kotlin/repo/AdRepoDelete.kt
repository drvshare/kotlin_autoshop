package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.repo.DbAdIdRequest
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == EAsState.RUNNING }
    handle {
        val request = DbAdIdRequest(adRepoPrepare)
        val result = adRepo.deleteAd(request)
        if (!result.isSuccess) {
            state = EAsState.FAILING
            errors.addAll(result.errors)
        }
        adRepoDone = adRepoRead
    }
}
