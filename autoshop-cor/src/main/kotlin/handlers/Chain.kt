package ru.drvshare.autoshop.cor.handlers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.drvshare.autoshop.cor.CorDslMarker
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.ICorExec
import ru.drvshare.autoshop.cor.base.BaseCorChain
import ru.drvshare.autoshop.cor.base.BaseCorChainDsl

@CorDslMarker
fun <T> ICorAddExecDsl<T>.chain(function: CorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    private val handler: suspend (List<ICorExec<T>>, T) -> Unit,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : BaseCorChain<T>(
    title = title,
    description = description,
    blockOn = blockOn,
    blockExcept = blockExcept
) {
    override suspend fun handle(context: T) = handler(execs, context)
}

/**
 * Стратегия последовательного исполнения
 */
suspend fun <T> executeSequential(execs: List<ICorExec<T>>, context: T): Unit =
    execs.forEach {
        it.exec(context)
    }

/**
 * Стратегия параллельного исполнения
 */
suspend fun <T> executeParallel(execs: List<ICorExec<T>>, context: T): Unit = coroutineScope {
    execs.forEach {
        launch { it.exec(context) }
    }
}

/**
 * DLS is the execution context of multiple chains.
 * It can be expanded by other chains.
 * The chains are executed sequentially.
 */
@CorDslMarker
class CorChainDsl<T>(
    private val handler: suspend (List<ICorExec<T>>, T) -> Unit = ::executeSequential,
) : BaseCorChainDsl<T,T>() {
    override fun build(): ICorExec<T> = CorChain(
        title = title,
        description = description,
        execs = workers.map { it.build() }.toList(),
        handler = handler,
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}
