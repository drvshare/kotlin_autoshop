package ru.drvshare.autoshop.cor.handlers

import ru.drvshare.autoshop.cor.CorDslMarker
import ru.drvshare.autoshop.cor.ICorChainDsl
import ru.drvshare.autoshop.cor.ICorExec
import ru.drvshare.autoshop.cor.ICorExecDsl
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Реализация цепочки (chain), которая исполняет свои вложенные цепочки и рабочие
 * в соответствии со стратегией handler
 */
class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    private val handler: suspend (List<ICorExec<T>>, T) -> Unit,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCorExec<T>(title, description, blockOn, blockExcept) {
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
suspend fun <T> executeParallel(context: T, execs: List<ICorExec<T>>): Unit = coroutineScope {
    execs.forEach {
        launch { it.exec(context) }
    }
}

@CorDslMarker
class CorChainDsl<T>(
    private val handler: suspend (List<ICorExec<T>>, T) -> Unit = ::executeSequential,
) : CorExecDsl<T>(), ICorChainDsl<T> {
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf()
    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
    }

    override fun build(): ICorExec<T> = CorChain(
        title = title,
        description = description,
        execs = workers.map { it.build() },
        handler = handler,
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}
