package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.models.EAsWorkMode
import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.chain


fun ICorChainDsl<AsAdContext>.stubs(title: String, block: ICorChainDsl<AsAdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == EAsWorkMode.STUB && state == EAsState.RUNNING }
}
