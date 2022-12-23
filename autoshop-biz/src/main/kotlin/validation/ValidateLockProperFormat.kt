package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.errorValidation
import ru.drvshare.autoshop.common.helpers.fail
import ru.drvshare.autoshop.common.models.AsAdLock
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker


fun ICorAddExecDsl<AsAdContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.lock != AsAdLock.NONE && !adValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
