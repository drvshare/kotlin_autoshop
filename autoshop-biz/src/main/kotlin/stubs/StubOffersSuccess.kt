package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub

fun ICorAddExecDsl<AsAdContext>.stubOffersSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.SUCCESS && state == EAsState.RUNNING }
    handle {
        state = EAsState.FINISHING
        adResponse = AsAdStub.prepareResult {
            adRequest.id.takeIf { it != AsAdId.NONE }?.also { this.id = it }
        }
        adsResponse.addAll(AsAdStub.prepareOffersList(adResponse.title, EAsDealSide.SUPPLY))
    }
}
