package ru.drvshare.autoshop.biz.general

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.models.EAsWorkMode
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != EAsWorkMode.STUB }
    handle {
        adResponse = adRepoDone
        adsResponse = adsRepoDone
        state = when (val st = state) {
            EAsState.RUNNING -> EAsState.FINISHING
            else -> st
        }
    }
}
