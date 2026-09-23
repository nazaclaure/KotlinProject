package edu.ucb.project.userinformation.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val email: String? = null,
    val company: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    val login: String? = null
)