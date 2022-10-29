package ru.drvshare.autoshop.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.drvshare.autoshop.api.v2.apiV2Mapper
import ru.drvshare.autoshop.api.v2.models.IRequest
import ru.drvshare.autoshop.api.v2.models.IResponse
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.asAutoShopError
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.mappers.v2.fromTransport
import ru.drvshare.autoshop.mappers.v2.toTransportAd

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2(
    processor: AsAdProcessor,
    command: EAsCommand? = null,
) {
    val ctx = AsAdContext(
        timeStart = Clock.System.now(),
    )
    try {
        val request = apiV2Mapper.decodeFromString<Q>(receiveText())
        ctx.fromTransport(request)
        processor.exec(ctx)
        respond(apiV2Mapper.encodeToString(ctx.toTransportAd()))
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = EAsState.FAILING
        ctx.errors.add(e.asAutoShopError())
        processor.exec(ctx)
        respond(apiV2Mapper.encodeToString(ctx.toTransportAd()))
    }
}
