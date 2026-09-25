package edu.ucb.project.signin.domain.usecase

import edu.ucb.project.signin.domain.model.UserModel
import edu.ucb.project.signin.domain.repository.AuthRepository
import edu.ucb.project.signin.domain.vo.Email
import edu.ucb.project.signin.domain.vo.Password

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: Email, password: Password): Result<UserModel> {
        if (!email.isValid()) return Result.failure(IllegalArgumentException("Correo inválido"))
        if (!password.isValid()) {
            return Result.failure(IllegalArgumentException("La contraseña debe tener al menos 6 caracteres"))
        }
        return repository.login(email, password)
    }
}
