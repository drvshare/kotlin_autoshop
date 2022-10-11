package ru.drvshare.autoshop.mappers.v1.exceptions

import kotlin.reflect.KClass

class UnknownRequestClass(clazz: KClass<*>): RuntimeException("Class $clazz cannot be mapped to AdContext")
