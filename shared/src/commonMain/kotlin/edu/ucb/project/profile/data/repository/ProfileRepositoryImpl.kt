package edu.ucb.project.profile.data.repository

import edu.ucb.project.profile.domain.model.ProfileModel
import edu.ucb.project.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.delay

class ProfileRepositoryImpl : ProfileRepository {
    override suspend fun getProfile(): ProfileModel {
        delay(500)
        return ProfileModel(
            id = "1",
            name = "Naza Claure",
            email = "naza@ucb.edu.bo"
        )
    }
}
