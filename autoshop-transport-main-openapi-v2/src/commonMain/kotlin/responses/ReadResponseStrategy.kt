package ru.drvshare.autoshop.api.v2.responses

import kotlinx.serialization.KSerializer
import ru.drvshare.autoshop.api.v2.models.AdReadResponse
import ru.drvshare.autoshop.api.v2.models.IResponse
import kotlin.reflect.KClass

object ReadResponseStrategy: IResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IResponse> = AdReadResponse::class
    override val serializer: KSerializer<out IResponse> = AdReadResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AdReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
