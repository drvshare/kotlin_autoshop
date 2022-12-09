package ru.drvshare.autoshop.app.base

import io.ktor.websocket.*
import ru.drvshare.autoshop.common.models.IClientSession

data class KtorUserSession(
    override val fwSession: WebSocketSession,
    override val apiVersion: String
) : IClientSession<WebSocketSession>
