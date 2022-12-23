package ru.drvshare.autoshop.common.repo

data class AsRepositories(
    val prod: IAdRepository = IAdRepository.NONE,
    val test: IAdRepository = IAdRepository.NONE,
) {
    companion object {
        val NONE = AsRepositories()
    }
}
