package ru.drvshare.autoshop.backend.repo.sql

import ru.drvshare.autoshop.backend.repo.common.*
import ru.drvshare.autoshop.common.repo.IAdRepository


class RepoAdSQLCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLReadTest : RepoAdReadTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
