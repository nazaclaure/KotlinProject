package edu.ucb.project.signin.data.repository

import edu.ucb.project.signin.domain.model.UserModel
import edu.ucb.project.signin.domain.repository.AuthRepository
import edu.ucb.project.signin.domain.vo.Email
import edu.ucb.project.signin.domain.vo.Password
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: Email, password: Password): Result<UserModel> {
        delay(500)
        return Result.success(
            UserModel(
                id = "1",
                email = email.value,
                token = "fake-token-123"
            )
        )
    }
}
