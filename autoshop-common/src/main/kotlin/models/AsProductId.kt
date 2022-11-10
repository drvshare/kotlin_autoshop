package ru.drvshare.autoshop.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AsProductId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = AsProductId("")
    }
}
