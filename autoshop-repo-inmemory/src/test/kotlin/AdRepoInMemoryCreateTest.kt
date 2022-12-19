import ru.drvshare.autoshop.backend.repo.common.RepoAdCreateTest
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoInMemory
import ru.drvshare.autoshop.common.repo.IAdRepository

class AdRepoInMemoryCreateTest: RepoAdCreateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
