package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.models.EAsWorkMode
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.chain


fun ICorAddExecDsl<AsAdContext>.stubs(title: String, block: ICorAddExecDsl<AsAdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == EAsWorkMode.STUB && state == EAsState.RUNNING }
}
