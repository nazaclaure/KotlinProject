package edu.ucb.project.userinformation.data.repository

import edu.ucb.project.userinformation.data.datasource.GithubRemoteDataSource
import edu.ucb.project.userinformation.data.mapper.toDomain
import edu.ucb.project.userinformation.domain.model.UserInfoModel
import edu.ucb.project.userinformation.domain.repository.GithubRepository

class GithubRepositoryImpl(val dataSource: GithubRemoteDataSource) : GithubRepository {
    override suspend fun findByAlias(alias: String): Result<UserInfoModel> {
        return try {
            Result.success(dataSource.getUser(alias).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}