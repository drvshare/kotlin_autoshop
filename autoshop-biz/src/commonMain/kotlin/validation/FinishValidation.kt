package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker


fun ICorChainDsl<AsAdContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == EAsState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorChainDsl<AsAdContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == EAsState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
