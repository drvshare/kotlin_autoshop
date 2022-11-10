package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.fail
import ru.drvshare.autoshop.common.models.AsError
import ru.drvshare.autoshop.common.models.EAsState

fun ICorAddExecDsl<AsAdContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == EAsState.RUNNING }
    handle {
        fail(
            AsError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
