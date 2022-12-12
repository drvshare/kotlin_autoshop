package ru.drvshare.autoshop.common.repo

import ru.drvshare.autoshop.common.models.AsAd
import ru.drvshare.autoshop.common.models.AsError

data class DbAdsResponse(
    override val data: List<AsAd>?,
    override val isSuccess: Boolean,
    override val errors: List<AsError> = emptyList(),
) : IDbResponse<List<AsAd>>
