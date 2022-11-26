package ru.drvshare.autoshop.app.v1

import io.ktor.websocket.*
import ru.drvshare.autoshop.api.v1.apiV1Mapper
import ru.drvshare.autoshop.api.v1.models.IRequest
import ru.drvshare.autoshop.app.base.KtorUserSession
import ru.drvshare.autoshop.app.base.mpWsHandler
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.mappers.v1.fromTransport
import ru.drvshare.autoshop.mappers.v1.toTransportAd
import ru.drvshare.autoshop.mappers.v1.toTransportInit

suspend fun WebSocketSession.asWsHandlerV1(
    processor: AsAdProcessor,
    sessions: MutableSet<KtorUserSession>
) = this.mpWsHandler(
    processor = processor,
    sessions = sessions,
    fromTransport = { fromTransport(apiV1Mapper.readValue(it, IRequest::class.java)) },
    toTransportInit = { apiV1Mapper.writeValueAsString(toTransportInit()) },
    toTransportBiz = { apiV1Mapper.writeValueAsString(toTransportAd()) },
)
