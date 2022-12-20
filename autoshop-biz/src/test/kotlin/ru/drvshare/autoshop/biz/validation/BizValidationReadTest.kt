package ru.drvshare.autoshop.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoStub
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.AsSettings
import ru.drvshare.autoshop.common.models.EAsCommand
import kotlin.test.Test

@Suppress("TestMethodWithoutAssertion")
@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val command = EAsCommand.READ
    private val settings by lazy {
        AsSettings(
            repoTest = AdRepoStub()
        )
    }

    private val processor by lazy { AsAdProcessor(settings) }

    @Test
    fun correctId() = validationIdCorrect(command, processor)
    @Test
    fun trimId() = validationIdTrim(command, processor)
    @Test
    fun emptyId() = validationIdEmpty(command, processor)
    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}

