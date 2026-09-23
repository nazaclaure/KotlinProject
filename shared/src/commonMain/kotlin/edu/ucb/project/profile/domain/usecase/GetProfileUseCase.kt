package edu.ucb.project.profile.domain.usecase

import edu.ucb.project.profile.domain.model.ProfileModel
import edu.ucb.project.profile.domain.repository.ProfileRepository

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(): ProfileModel = repository.getProfile()
}
