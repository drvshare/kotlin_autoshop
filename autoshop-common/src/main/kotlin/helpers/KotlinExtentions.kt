package ru.drvshare.autoshop.common.helpers

import mu.KLogger
import mu.KotlinLogging
import kotlin.reflect.full.companionObject

val Any.KLOGGER: KLogger
    get() = KotlinLogging.logger(unwrapCompanionClass(this.javaClass).name)

fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
        ofClass.enclosingClass
    } else {
        ofClass
    }
}
