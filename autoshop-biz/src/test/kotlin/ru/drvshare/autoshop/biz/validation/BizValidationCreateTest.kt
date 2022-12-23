package ru.drvshare.autoshop.biz.validation

import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoStub
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.AsSettings
import ru.drvshare.autoshop.common.models.EAsCommand
import kotlin.test.Test

@Suppress("TestMethodWithoutAssertion")
class BizValidationCreateTest {

    private val command = EAsCommand.CREATE
    private val settings by lazy {
        AsSettings(
            repoTest = AdRepoStub()
        )
    }
    private val processor by lazy { AsAdProcessor(settings) }

    @Test
    fun correctTitle() = validationTitleCorrect(command, processor)
    @Test
    fun trimTitle() = validationTitleTrim(command, processor)
    @Test
    fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test
    fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test
    fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test
    fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test
    fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test
    fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

}

