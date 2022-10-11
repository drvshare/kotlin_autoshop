package ru.drvshare.autoshop.api.v2.responses

import kotlinx.serialization.KSerializer
import ru.drvshare.autoshop.api.v2.models.AdOffersResponse
import ru.drvshare.autoshop.api.v2.models.IResponse
import kotlin.reflect.KClass

object OffersResponseStrategy: IResponseStrategy {
    override val discriminator: String = "offers"
    override val clazz: KClass<out IResponse> = AdOffersResponse::class
    override val serializer: KSerializer<out IResponse> = AdOffersResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AdOffersResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
