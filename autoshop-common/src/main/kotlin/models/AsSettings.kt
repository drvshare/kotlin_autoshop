package ru.drvshare.autoshop.common.models

import ru.drvshare.autoshop.common.repo.IAdRepository

data class AsSettings(
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
)
