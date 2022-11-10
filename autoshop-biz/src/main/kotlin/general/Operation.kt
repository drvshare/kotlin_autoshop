package ru.drvshare.autoshop.biz.general

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.chain

fun ICorAddExecDsl<AsAdContext>.operation(title: String, command: EAsCommand, block: ICorAddExecDsl<AsAdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == EAsState.RUNNING }
}
