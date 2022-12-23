package ru.drvshare.autoshop.backend.repository.inmemory

import ru.drvshare.autoshop.backend.repo.common.RepoAdDeleteTest
import ru.drvshare.autoshop.common.repo.IAdRepository

class AdRepoInMemoryDeleteTest: RepoAdDeleteTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
