package ru.drvshare.autoshop.api.v2.responses

import kotlinx.serialization.KSerializer
import ru.drvshare.autoshop.api.v2.models.AdSearchResponse
import ru.drvshare.autoshop.api.v2.models.IResponse
import kotlin.reflect.KClass

object SearchResponseStrategy: IResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IResponse> = AdSearchResponse::class
    override val serializer: KSerializer<out IResponse> = AdSearchResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AdSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
