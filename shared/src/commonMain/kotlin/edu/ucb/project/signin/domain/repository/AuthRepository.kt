package edu.ucb.project.signin.domain.repository

import edu.ucb.project.signin.domain.model.UserModel

interface AuthRepository {
    suspend fun login(email: String, pass: String): UserModel
}
