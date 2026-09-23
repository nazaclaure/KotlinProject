package edu.ucb.project.signup.domain.repository

import edu.ucb.project.signup.domain.model.SignUpModel

interface SignUpRepository {
    suspend fun register(name: String, email: String, pass: String): SignUpModel
}
