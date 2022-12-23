package ru.drvshare.autoshop.common.repo

import ru.drvshare.autoshop.common.models.AsError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<AsError>
}
