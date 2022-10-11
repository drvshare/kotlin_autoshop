package ru.drvshare.autoshop.api.v2.responses

import ru.drvshare.autoshop.api.v2.IApiStrategy
import ru.drvshare.autoshop.api.v2.models.IResponse

sealed interface IResponseStrategy: IApiStrategy<IResponse> {
    companion object {
        private val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            DeleteResponseStrategy,
            SearchResponseStrategy,
            OffersResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
