package ru.drvshare.autoshop.biz.groups

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.chain

fun ICorChainDsl<AsAdContext>.operation(title: String, command: EAsCommand, block: ICorChainDsl<AsAdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == EAsState.RUNNING }
}
