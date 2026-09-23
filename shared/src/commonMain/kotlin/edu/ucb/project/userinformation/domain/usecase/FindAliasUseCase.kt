package edu.ucb.project.userinformation.domain.usecase

import edu.ucb.project.userinformation.domain.model.UserInfoModel
import edu.ucb.project.userinformation.domain.repository.GithubRepository

class FindAliasUseCase(
    val repository: GithubRepository
) {
    suspend fun invoke(alias: String): Result<UserInfoModel> {
        return repository.findByAlias(alias)
    }
}