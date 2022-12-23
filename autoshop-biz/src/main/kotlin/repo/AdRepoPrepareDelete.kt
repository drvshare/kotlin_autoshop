package ru.drvshare.autoshop.biz.repo

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == EAsState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
    }
}
