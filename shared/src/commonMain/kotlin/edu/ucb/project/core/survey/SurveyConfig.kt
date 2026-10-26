package edu.ucb.project.core.survey

object SurveyConfig {
    // Enlace publico de la encuesta en SurveyMonkey
    const val SURVEY_URL = "https://www.surveymonkey.com/r/R3NF5R8"

    // Para mostrar la invitacion solo una vez por sesion de la app
    var promptShown: Boolean = false
}