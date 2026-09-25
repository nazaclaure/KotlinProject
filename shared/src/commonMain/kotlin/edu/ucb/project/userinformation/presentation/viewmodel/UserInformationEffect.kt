package edu.ucb.project.userinformation.presentation.viewmodel

sealed interface UserInformationEffect {
    data class ShowToast(val message: String) : UserInformationEffect
    object NavigateToBack : UserInformationEffect
    object NavigateToHome : UserInformationEffect
}
