package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.models.EAsAdVisibility
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.stubs.AsAdStub

fun ICorAddExecDsl<AsAdContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.SUCCESS && state == EAsState.RUNNING }
    handle {
        state = EAsState.FINISHING
        val stub = AsAdStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            adRequest.adType.takeIf { it != EAsDealSide.NONE }?.also { this.adType = it }
            adRequest.visibility.takeIf { it != EAsAdVisibility.NONE }?.also { this.visibility = it }
        }
        adResponse = stub
    }
}
