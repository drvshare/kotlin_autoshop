package ru.drvshare.autoshop.backend.repository.inmemory

import ru.drvshare.autoshop.common.models.EAsDealSide
import ru.drvshare.autoshop.common.repo.*
import ru.drvshare.autoshop.stubs.AsAdStub

class AdRepoStub() : IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = AsAdStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = AsAdStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = AsAdStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = AsAdStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return DbAdsResponse(
            data = AsAdStub.prepareSearchList(filter = "", EAsDealSide.DEMAND),
            isSuccess = true,
        )
    }
}
