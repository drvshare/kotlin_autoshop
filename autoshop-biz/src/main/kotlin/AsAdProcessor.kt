package ru.drvshare.autoshop.biz


import ru.drvshare.autoshop.biz.general.operation
import ru.drvshare.autoshop.biz.general.prepareResult
import ru.drvshare.autoshop.biz.general.initRepo
import ru.drvshare.autoshop.biz.stubs.*
import ru.drvshare.autoshop.biz.validation.*
import ru.drvshare.autoshop.biz.repo.*
import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.models.*
import ru.drvshare.autoshop.cor.handlers.chain
import ru.drvshare.autoshop.cor.rootChain
import ru.drvshare.autoshop.cor.handlers.worker

class AsAdProcessor(private val settings: AsSettings = AsSettings()) {
    suspend fun exec(ctx: AsAdContext) = BusinessChain.exec(ctx.apply { settings = this@AsAdProcessor.settings })

    companion object {
        @Suppress("DuplicatedCode")
        private val BusinessChain = rootChain<AsAdContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание объявления", EAsCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка заголовка") { adValidating.title = adValidating.title.trim() }
                    worker("Очистка описания") { adValidating.description = adValidating.description.trim() }
                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание объявления в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить объявление", EAsCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка id") { adValidating.id = AsAdId(adValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение объявления из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == EAsState.RUNNING }
                        handle { adRepoDone = adRepoRead }
                    }
                }
                prepareResult("Подготовка ответа")
            }
            operation("Изменить объявление", EAsCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка id") { adValidating.id = AsAdId(adValidating.id.asString().trim()) }
                    worker("Очистка lock") { adValidating.lock = AsAdLock(adValidating.lock.asString().trim()) }
                    worker("Очистка заголовка") { adValidating.title = adValidating.title.trim() }
                    worker("Очистка описания") { adValidating.description = adValidating.description.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateLockNotEmpty("Проверка на непустой lock")
                    validateLockProperFormat("Проверка формата lock")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение объявления из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление объявления в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Удалить объявление", EAsCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") {
                        adValidating = adRequest.deepCopy()
                    }
                    worker("Очистка id") { adValidating.id = AsAdId(adValidating.id.asString().trim()) }
                    worker("Очистка lock") { adValidating.lock = AsAdLock(adValidating.lock.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateLockNotEmpty("Проверка на непустой lock")
                    validateLockProperFormat("Проверка формата lock")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение объявления из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление объявления из БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Поиск объявлений", EAsCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { adFilterValidating = adFilterRequest.copy() }

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
                repoSearch("Поиск объявления в БД по фильтру")
                prepareResult("Подготовка ответа")
            }
            operation("Поиск подходящих предложений для объявления", EAsCommand.OFFERS) {
                stubs("Обработка стабов") {
                    stubOffersSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка id") { adValidating.id = AsAdId(adValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика поиска в БД"
                    repoRead("Чтение объявления из БД")
                    repoPrepareOffers("Подготовка данных для поиска предложений")
                    repoOffers("Поиск предложений для объявления в БД")
                }
                prepareResult("Подготовка ответа")
            }
        }.build()
    }

}
