package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState

fun ICorChainDsl<AsAdContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == EAsState.NONE }
    handle { state = EAsState.RUNNING }
}
