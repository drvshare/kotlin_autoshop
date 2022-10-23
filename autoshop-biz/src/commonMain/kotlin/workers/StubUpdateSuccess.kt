package ru.drvshare.autoshop.biz.workers

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsAdId
import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.models.EAsAdVisibility
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub

fun ICorChainDsl<AsAdContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.SUCCESS && state == EAsState.RUNNING }
    handle {
        state = EAsState.FINISHING
        val stub = AsAdStub.prepareResult {
            adRequest.id.takeIf { it != AsAdId.NONE }?.also { this.id = it }
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            adRequest.adType.takeIf { it != EAsDealSide.NONE }?.also { this.adType = it }
            adRequest.visibility.takeIf { it != EAsAdVisibility.NONE }?.also { this.visibility = it }
        }
        adResponse = stub
    }
}
