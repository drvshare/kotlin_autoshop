import ru.drvshare.autoshop.backend.repo.common.RepoAdReadTest
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoInMemory
import ru.drvshare.autoshop.common.repo.IAdRepository

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
