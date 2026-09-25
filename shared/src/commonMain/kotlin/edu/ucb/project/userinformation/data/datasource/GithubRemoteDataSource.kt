package edu.ucb.project.userinformation.data.datasource

import edu.ucb.project.userinformation.data.dto.UserInfoDto

interface GithubRemoteDataSource {
    suspend fun getUser(nickname: String): UserInfoDto
}
