package ru.drvshare.autoshop.cor.handlers

import ru.drvshare.autoshop.cor.CorDslMarker
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.ICorExec
import ru.drvshare.autoshop.cor.base.BaseCorChain
import ru.drvshare.autoshop.cor.base.BaseCorChainDsl
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

@CorDslMarker
fun <T, K> ICorAddExecDsl<T>.subChain(function: CorSubChainDsl<T, K>.() -> Unit) {
    add(CorSubChainDsl<T, K>().apply(function))
}

class CorSubChain<T, K>(
    private val execs: List<ICorExec<K>>,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    private val blockSplit: suspend T.() -> Flow<K>,
    private val blockJoin: suspend T.(K) -> Unit,
    blockExcept: suspend T.(Throwable) -> Unit = {},
    private val buffer: Int = 1,
) : BaseCorChain<T>(
    title = title,
    description = description,
    blockOn = blockOn,
    blockExcept = blockExcept
) {

    override suspend fun handle(context: T): Unit = coroutineScope {
        context
            .blockSplit()
            .map { subCtx -> async { execs.forEach { it.exec(subCtx) }; subCtx } }
            .run { if (buffer > 0) buffer(buffer) else this }
            .collect { context.blockJoin(it.await()) }
    }
}

/**
 * DLS is the execution context of multiple chains.
 * It can be expanded by other chains.
 */
@CorDslMarker
class CorSubChainDsl<T, K>(
) : BaseCorChainDsl<T, K>() {
    private var blockSplit: suspend T.() -> Flow<K> = { emptyFlow() }
    private var blockJoin: suspend T.(K) -> Unit = {}
    private var bufferSize: Int = 0

    fun buffer(size: Int) {
        require(size >= 0) {
            "Buffer must be more or equal than zero. Zero means unbuffered execution"
        }
        bufferSize = size
    }

    fun split(funSplit: suspend T.() -> Flow<K>) {
        blockSplit = funSplit
    }

    fun join(funJoin: suspend T.(K) -> Unit) {
        blockJoin = funJoin
    }

    override fun build(): ICorExec<T> = CorSubChain(
        title = title,
        description = description,
        execs = workers.map { it.build() }.toList(),
        blockOn = blockOn,
        blockExcept = blockExcept,
        blockSplit = blockSplit,
        blockJoin = blockJoin,
        buffer = bufferSize
    )
}