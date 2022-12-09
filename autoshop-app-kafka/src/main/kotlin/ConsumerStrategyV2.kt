package ru.drvshare.autoshop.app.kafka

import ru.drvshare.autoshop.api.v1.apiV1RequestDeserialize
import ru.drvshare.autoshop.api.v1.apiV1ResponseSerialize
import ru.drvshare.autoshop.api.v1.models.IRequest
import ru.drvshare.autoshop.api.v1.models.IResponse
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.mappers.v1.fromTransport
import ru.drvshare.autoshop.mappers.v1.toTransportAd

class ConsumerStrategyV2{}

/*
class ConsumerStrategyV2 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV2, config.kafkaTopicOutV2)
    }

    override fun serialize(source: AsAdContext): String {
        val response: IResponse = source.toTransportAd()
        return apiV2ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: AsAdContext) {
        val request: IRequest = apiV2RequestDeserialize(value)
        target.fromTransport(request)
    }
}
*/
