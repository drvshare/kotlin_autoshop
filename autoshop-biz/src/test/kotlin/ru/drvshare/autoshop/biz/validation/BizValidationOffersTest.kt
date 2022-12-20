package ru.drvshare.autoshop.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.drvshare.autoshop.biz.AsAdProcessor
import ru.drvshare.autoshop.common.models.EAsCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationOffersTest {

    private val command = EAsCommand.OFFERS
    private val processor by lazy { AsAdProcessor() }

    @Test
    fun correctId() = validationIdCorrect(command, processor)
    @Test
    fun trimId() = validationIdTrim(command, processor)
    @Test
    fun emptyId() = validationIdEmpty(command, processor)
    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}

