package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker
import ru.drvshare.autoshop.common.helpers.errorValidation
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.fail
import ru.drvshare.autoshop.common.models.AsAdId

fun ICorChainDsl<AsAdContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в AsAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.id != AsAdId.NONE && ! adValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only"
        )
        )
    }
}
