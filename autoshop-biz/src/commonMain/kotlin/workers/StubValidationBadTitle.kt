package ru.drvshare.autoshop.biz.workers

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsError
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.common.stubs.EAsAdStubs

fun ICorChainDsl<AsAdContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    on { stubCase == EAsAdStubs.BAD_TITLE && state == EAsState.RUNNING }
    handle {
        state = EAsState.FAILING
        this.errors.add(
            AsError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}
