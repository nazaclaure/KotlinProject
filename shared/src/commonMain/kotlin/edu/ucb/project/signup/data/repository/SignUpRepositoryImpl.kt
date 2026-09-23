package edu.ucb.project.signup.data.repository

import edu.ucb.project.signup.domain.model.SignUpModel
import edu.ucb.project.signup.domain.repository.SignUpRepository
import kotlinx.coroutines.delay

class SignUpRepositoryImpl : SignUpRepository {
    override suspend fun register(name: String, email: String, pass: String): SignUpModel {
        delay(500)
        return SignUpModel(id = "1", email = email)
    }
}