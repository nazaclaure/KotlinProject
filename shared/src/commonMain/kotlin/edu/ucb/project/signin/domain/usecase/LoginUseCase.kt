package edu.ucb.project.signin.domain.usecase

import edu.ucb.project.signin.domain.model.UserModel
import edu.ucb.project.signin.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String): UserModel {
        return repository.login(email, pass)
    }
}
