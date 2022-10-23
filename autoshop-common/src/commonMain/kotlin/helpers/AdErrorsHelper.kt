package ru.drvshare.autoshop.common.helpers

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.AsError
import ru.drvshare.autoshop.common.models.EAsState

fun Throwable.asAutoShopError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = AsError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun AsAdContext.addError(error: AsError) = errors.add(error)
fun AsAdContext.fail(error: AsError) {
    addError(error)
    state = EAsState.FAILING
}
