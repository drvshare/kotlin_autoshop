package ru.drvshare.autoshop.biz.stubs

import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsError
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.stubs.EAsAdStubs

fun ICorAddExecDsl<AsAdContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.DB_ERROR && state == EAsState.RUNNING }
    handle {
        state = EAsState.FAILING
        this.errors.add(
            AsError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
