package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == EAsState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorAddExecDsl<AsAdContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == EAsState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
