package ru.drvshare.autoshop.cor

import ru.drvshare.autoshop.cor.handlers.chain
import ru.drvshare.autoshop.cor.handlers.worker
import ru.drvshare.autoshop.cor.helper.TestContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CorExceptionTest {

    @Test
    fun `on exception - positive`() = runTest {
        val testText = "test1"
        val ctx = TestContext()
        val chain = rootChain<TestContext> {
            worker {
                handle { throw RuntimeException(testText) }
                except { e -> text = e.message ?: "" }
            }
        }.build()

        chain.exec(ctx)

        assertEquals(testText, ctx.text)
    }

    @Test
    fun `two exception - negative`() = runTest {
        val testText1 = "test1"
        val testText2 = "test2"
        val ctx = TestContext()
        val chain = rootChain<TestContext> {
            chain {
                worker {
                    handle { throw RuntimeException(testText1) }
                    except { e -> text = e.message ?: "" }
                }
                except { e -> text = e.message ?: "" }
            }
        }.build()

        chain.exec(ctx)

        assertNotEquals(testText2, ctx.text)
    }

    @Test
    fun `two exceptions - positive`() = runTest {
        val testText1 = "test1"
        val testText2 = "test2"
        val ctx = TestContext()
        val chain = rootChain<TestContext> {
            chain {
                worker {
                    handle { throw RuntimeException(testText1) }
                    except { e ->
                        text = e.message ?: ""
                        throw e
                    }
                }
                except { e -> text = "${e.message}$testText2" }
            }
        }.build()

        chain.exec(ctx)

        assertEquals("$testText1$testText2", ctx.text)
    }

}
