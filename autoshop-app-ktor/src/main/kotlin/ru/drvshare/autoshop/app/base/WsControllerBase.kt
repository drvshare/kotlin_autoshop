package ru.drvshare.autoshop.app.base

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.Clock
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.addError
import ru.drvshare.autoshop.common.helpers.asAutoShopError
import ru.drvshare.autoshop.common.models.EAsCommand

suspend fun WebSocketSession.mpWsHandler(
    processor: AsAdProcessor,
    sessions: MutableSet<KtorUserSession>,
    fromTransport: AsAdContext.(request: String) -> Unit,
    toTransportBiz: AsAdContext.() -> String,
    toTransportInit: AsAdContext.() -> String,
) {
    val userSession = KtorUserSession(this, "v1")
    sessions.add(userSession)
    println(sessions.size)
    run {
        val ctx = AsAdContext(
            timeStart = Clock.System.now()
        )
        // обработка запроса на инициализацию
        outgoing.send(Frame.Text(ctx.toTransportInit()))
    }
    incoming
        .receiveAsFlow()
        .mapNotNull { it as? Frame.Text }
        .map { frame ->
            val ctx = AsAdContext(
                timeStart = Clock.System.now()
            )
            // Обработка исключений без завершения flow
            try {
                val jsonStr = frame.readText()
                ctx.fromTransport(jsonStr)
                processor.exec(ctx)
                // Если произошли изменения, то ответ отправляется всем
                if (ctx.isUpdatableCommand()) {
                    sessions.forEach {
                        it.fwSession.send(Frame.Text(ctx.toTransportBiz()))
                    }
                } else {
                    outgoing.send(Frame.Text(ctx.toTransportBiz()))
                }
            } catch (_: ClosedReceiveChannelException) {
                sessions.remove(userSession)
            } catch (t: Throwable) {
                ctx.addError(t.asAutoShopError())
                outgoing.send(Frame.Text(ctx.toTransportInit()))
            }
        }
        .collect()
}

private fun AsAdContext.isUpdatableCommand() =
    this.command in listOf(EAsCommand.CREATE, EAsCommand.UPDATE, EAsCommand.DELETE)
