package edu.ucb.project.signup.domain.usecase

import edu.ucb.project.signup.domain.model.SignUpModel
import edu.ucb.project.signup.domain.repository.SignUpRepository

class RegisterUseCase(private val repository: SignUpRepository) {
    suspend operator fun invoke(name: String, email: String, pass: String): SignUpModel {
        return repository.register(name, email, pass)
    }
}
