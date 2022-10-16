package ru.drvshare.autoshop.common.helpers

import ru.drvshare.autoshop.common.AdContext
import ru.drvshare.autoshop.common.models.AdError
import ru.drvshare.autoshop.common.models.EAdState

fun Throwable.asAutoShopError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = AdError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun AdContext.addError(error: AdError) = errors.add(error)
fun AdContext.fail(error: AdError) {
    addError(error)
    state = EAdState.FAILING
}
