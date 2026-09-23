package edu.ucb.project.signin.data.repository

import edu.ucb.project.signin.domain.model.UserModel
import edu.ucb.project.signin.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, pass: String): UserModel {
        delay(500)
        if (email.isBlank() || pass.isBlank()) {
            throw Exception("Correo y contraseña son requeridos")
        }
        return UserModel(
            id = "1",
            email = email,
            token = "fake-token-123"
        )
    }
}