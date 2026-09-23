package edu.ucb.project.userinformation.domain.repository

import edu.ucb.project.userinformation.domain.model.UserInfoModel

interface GithubRepository {
    suspend fun findByAlias(alias: String): Result<UserInfoModel>
}