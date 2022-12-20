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

fun AsAdContext.addError(vararg error: AsError) = errors.addAll(error)
fun AsAdContext.fail(vararg error: AsError) {
    addError(*error)
    state = EAsState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: AsError.Levels = AsError.Levels.ERROR,
) = AsError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorMapping(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: AsError.Levels = AsError.Levels.ERROR,
) = AsError(
    code = "mapping-$field-$violationCode",
    field = field,
    group = "mapping",
    message = "Mapping error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: AsError.Levels = AsError.Levels.ERROR,
) = AsError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)
