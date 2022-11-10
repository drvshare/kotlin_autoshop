package ru.drvshare.autoshop.cor

import ru.drvshare.autoshop.cor.handlers.loopUntil
import ru.drvshare.autoshop.cor.handlers.loopWhile
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.cor.helper.CorStatuses
import ru.drvshare.autoshop.cor.helper.TestContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LoopCorBaseTest {
    @Test
    fun loopUntil() = runTest {
        val ctx = TestContext(some = 0)
        loopUntil.exec(ctx)
        assertEquals(CorStatuses.RUNNING, ctx.status)
        assertEquals(10, ctx.some)
        assertEquals("", ctx.text)
    }

    @Test
    fun loopWhile() = runTest {
        val ctx = TestContext(some = 0)
        loopWhile.exec(ctx)
        assertEquals(CorStatuses.RUNNING, ctx.status)
        assertEquals(10, ctx.some)
        assertEquals("", ctx.text)
    }

    companion object {
        val loopUntil = rootChain<TestContext> {
            worker {
                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
            }
            loopUntil {
                title = "Looping"
                description = "Repeat the business chain until the condition is met"

                on { true }
                check { some < 5 }
                except { status = CorStatuses.FAILING }
                worker(title = "Increment some") {
                    some++
                    println("=$some")
                }
            }
            loopUntil {
                check { some < 10 }
                except { status = CorStatuses.FAILING }
                worker(title = "Increment some") {
                    some++
                    println("=$some")
                }
            }
        }.build()

        val loopWhile = rootChain<TestContext> {
            worker {
                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
            }
            loopWhile {
                title = "Looping"
                description = "Repeat the business chain until the condition is met"

                on { true }
                check { some < 5 }
                except { status = CorStatuses.FAILING }
                worker(title = "Increment some") {
                    some++
                    println("=$some")
                }
            }
            loopWhile {
                check { some < 10 }
                except { status = CorStatuses.FAILING }
                worker(title = "Increment some") {
                    some++
                    println("=$some")
                }
            }
        }.build()

    }
}
