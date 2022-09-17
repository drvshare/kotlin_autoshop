package ru.drvshare.autoshop.api.v2.responses

import kotlinx.serialization.KSerializer
import ru.drvshare.autoshop.api.v2.models.AdCreateResponse
import ru.drvshare.autoshop.api.v2.models.IResponse
import kotlin.reflect.KClass

object CreateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IResponse> = AdCreateResponse::class
    override val serializer: KSerializer<out IResponse> = AdCreateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AdCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
