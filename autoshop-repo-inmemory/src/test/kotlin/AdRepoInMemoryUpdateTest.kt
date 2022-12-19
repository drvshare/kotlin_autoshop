import ru.drvshare.autoshop.backend.repo.common.RepoAdUpdateTest
import ru.drvshare.autoshop.backend.repository.inmemory.AdRepoInMemory
import ru.drvshare.autoshop.common.repo.IAdRepository

class AdRepoInMemoryUpdateTest: RepoAdUpdateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
