package ru.drvshare.autoshop.backend.repository.inmemory

import ru.drvshare.autoshop.backend.repo.common.RepoAdCreateTest

class AdRepoInMemoryCreateTest: RepoAdCreateTest() {
    override val repo = AdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
