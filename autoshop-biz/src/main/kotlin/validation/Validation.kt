package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.chain

fun ICorAddExecDsl<AsAdContext>.validation(block: ICorAddExecDsl<AsAdContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == EAsState.RUNNING }
}
