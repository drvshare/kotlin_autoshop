package ru.drvshare.autoshop.biz.workers

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsError
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.stubs.EAsAdStubs

fun ICorChainDsl<AsAdContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.BAD_ID && state == EAsState.RUNNING }
    handle {
        state = EAsState.FAILING
        this.errors.add(
            AsError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
