package ru.drvshare.autoshop.biz.workers

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub

fun ICorChainDsl<AsAdContext>.stubOffersSuccess(title: String) = worker {
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
