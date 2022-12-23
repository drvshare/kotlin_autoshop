package ru.drvshare.autoshop.common.repo

import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.AsError

data class DbAdResponse(
    override val data: AsAd?,
    override val isSuccess: Boolean,
    override val errors: List<AsError> = emptyList()
): IDbResponse<AsAd>{

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        val MOCK_SUCCESS_NONE get() = DbAdResponse(AsAd.NONE, true)
    }
}
