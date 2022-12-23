package ru.drvshare.autoshop.biz.general

import ru.drvshare.autoshop.common.AsAdContext
import ru.drvshare.autoshop.common.helpers.errorAdministration
import ru.drvshare.autoshop.common.helpers.fail
import ru.drvshare.autoshop.common.models.EAsWorkMode
import ru.drvshare.autoshop.common.repo.IAdRepository
import ru.drvshare.autoshop.cor.ICorAddExecDsl
import ru.drvshare.autoshop.cor.handlers.worker

fun ICorAddExecDsl<AsAdContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when (workMode) {
            EAsWorkMode.TEST -> settings.repoTest
            EAsWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != EAsWorkMode.STUB && adRepo == IAdRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
