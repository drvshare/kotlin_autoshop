package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.errorValidation
import ru.drvshare.autoshop.common.helpers.fail
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
