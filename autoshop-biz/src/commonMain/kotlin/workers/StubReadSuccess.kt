package ru.drvshare.autoshop.biz.workers

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub

fun ICorChainDsl<AsAdContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.SUCCESS && state == EAsState.RUNNING }
    handle {
        state = EAsState.FINISHING
        val stub = AsAdStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        adResponse = stub
    }
}
