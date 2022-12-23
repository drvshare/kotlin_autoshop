package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.repoPrepareOffers(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == EAsState.RUNNING }
    handle {
        adRepoPrepare = adRepoRead.deepCopy()
        adRepoDone = adRepoRead.deepCopy()
    }
}
