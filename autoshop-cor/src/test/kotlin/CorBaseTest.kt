package ru.drvshare.autoshop.cor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.drvshare.autoshop.cor.handlers.chain
import ru.drvshare.autoshop.cor.handlers.parallel
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.cor.helper.CorStatuses
import ru.drvshare.autoshop.cor.helper.TestContext
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CorBaseTest {
    @Test
    fun createCor() = runTest {
        val ctx = TestContext(some = 0)
        chain.exec(ctx)
        assertEquals(CorStatuses.RUNNING, ctx.status)
        assertEquals(5, ctx.some)
        assertEquals("", ctx.text)
    }

    companion object {
        val chain = rootChain<TestContext> {
            worker {
                title = "Status initialization"
                description = "Check the status initialization at the buziness chain start"

                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
                except { status = CorStatuses.FAILING }
            }

            chain {
                on { status == CorStatuses.RUNNING }

                worker(
                    title = "Lambda worker",
                    description = "Example of a buziness chain worker in a lambda form"
                ) {
                    some += 4
                }
            }

            parallel {
                on {
                    some < 15
                }
                worker(title = "Increment some") {
                    some++
                }
            }
            printResult()

        }.build()

    }
}

private fun ICorAddExecDsl<TestContext>.printResult() = worker(title = "Print example") {
    println("some = $some")
}

