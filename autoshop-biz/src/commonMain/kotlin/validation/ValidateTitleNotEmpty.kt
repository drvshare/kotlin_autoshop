package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.helpers.errorValidation
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.fail

fun ICorChainDsl<AsAdContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.title.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
