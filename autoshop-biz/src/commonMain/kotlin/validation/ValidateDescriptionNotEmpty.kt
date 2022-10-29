package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.helpers.errorValidation
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.fail

fun ICorChainDsl<AsAdContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "description",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
