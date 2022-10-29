package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.chain

fun ICorChainDsl<AsAdContext>.validation(block: ICorChainDsl<AsAdContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == EAsState.RUNNING }
}
