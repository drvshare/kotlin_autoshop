package ru.drvshare.autoshop.backend.repository.inmemory

import ru.drvshare.autoshop.backend.repo.common.RepoAdSearchTest
import ru.drvshare.autoshop.common.repo.IAdRepository

class AdRepoInMemorySearchTest: RepoAdSearchTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
