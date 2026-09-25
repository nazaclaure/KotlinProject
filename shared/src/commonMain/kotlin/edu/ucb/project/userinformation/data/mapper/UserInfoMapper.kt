package edu.ucb.project.userinformation.data.mapper

import edu.ucb.project.userinformation.data.dto.UserInfoDto
import edu.ucb.project.userinformation.domain.model.UserInfoModel

fun UserInfoDto.toDomain(): UserInfoModel = UserInfoModel(
    email = email ?: "",
    company = company ?: "",
    avatarUrl = avatarUrl ?: "",
    alias = login ?: ""
)
