package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.common.helpers.errorValidation
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.fail
import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.worker

fun ICorChainDsl<AsAdContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.description.isNotEmpty() && ! adValidating.description.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "description",
            violationCode = "noContent",
            description = "field must contain leters"
        )
        )
    }
}
