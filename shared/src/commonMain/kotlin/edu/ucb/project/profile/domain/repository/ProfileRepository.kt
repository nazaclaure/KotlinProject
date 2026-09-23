package edu.ucb.project.profile.domain.repository

import edu.ucb.project.profile.domain.model.ProfileModel

interface ProfileRepository {
    suspend fun getProfile(): ProfileModel
}
