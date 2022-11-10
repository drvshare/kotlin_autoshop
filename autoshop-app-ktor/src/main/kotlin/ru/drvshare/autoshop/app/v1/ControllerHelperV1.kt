package ru.drvshare.autoshop.app.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import ru.drvshare.autoshop.api.v1.models.IRequest
import ru.drvshare.autoshop.api.v1.models.IResponse
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.asAutoShopError
import ru.drvshare.autoshop.common.models.EAsCommand
import ru.drvshare.autoshop.common.models.EAsState
import ru.drvshare.autoshop.mappers.v1.fromTransport
import ru.drvshare.autoshop.mappers.v1.toTransportAd

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    processor: AsAdProcessor,
    command: EAsCommand? = null,
) {
    val ctx = AsAdContext(
        timeStart = Clock.System.now(),
    )
    try {
        val request = receive<Q>()
        ctx.fromTransport(request)
        processor.exec(ctx)
        respond(ctx.toTransportAd())
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = EAsState.FAILING
        ctx.errors.add(e.asAutoShopError())
        processor.exec(ctx)
        respond(ctx.toTransportAd())
    }
}
