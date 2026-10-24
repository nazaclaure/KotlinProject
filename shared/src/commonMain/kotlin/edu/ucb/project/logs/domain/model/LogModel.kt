package edu.ucb.project.logs.domain.model

enum class LogLevel { INFO, DEBUG, TRACE, WARN, ERROR, FATAL }

data class LogModel(
    val androidId: String,
    val appInstanceId: String,
    val level: LogLevel,
    val message: String,
    val stackTrace: String,
    val timestamp: Long,
    val versionCode: Int,
    val userId: String
)