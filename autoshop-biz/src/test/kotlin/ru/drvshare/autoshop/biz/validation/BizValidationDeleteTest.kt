package ru.drvshare.autoshop.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoStub
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.AsSettings
import ru.drvshare.autoshop.common.models.EAsCommand
import kotlin.test.Test

@Suppress("TestMethodWithoutAssertion")
@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = EAsCommand.DELETE
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

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)

}

