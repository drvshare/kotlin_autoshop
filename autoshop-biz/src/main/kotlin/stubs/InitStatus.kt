package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.handlers.worker

fun ICorAddExecDsl<AsAdContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == EAsState.NONE }
    handle { state = EAsState.RUNNING }
}
