package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.common.stubs.EAsAdStubs
import ru.drvshare.autoshop.stubs.AsAdStub

fun ICorChainDsl<AsAdContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.SUCCESS && state == EAsState.RUNNING }
    handle {
        state = EAsState.FINISHING
        adsResponse.addAll(AsAdStub.prepareSearchList(adFilterRequest.searchString, EAsDealSide.DEMAND))
    }
}
