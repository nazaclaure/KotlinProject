package edu.ucb.project.logs.data.mapper

import com.develoop.logs.ELogLevel
import com.develoop.logs.LogData
import edu.ucb.project.logs.domain.model.LogLevel
import edu.ucb.project.logs.domain.model.LogModel
import okio.ByteString.Companion.encodeUtf8

fun LogLevel.toDto(): ELogLevel = when (this) {
    LogLevel.INFO -> ELogLevel.LEVEL_INFO
    LogLevel.DEBUG -> ELogLevel.LEVEL_DEBUG
    LogLevel.TRACE -> ELogLevel.LEVEL_TRACE
    LogLevel.WARN -> ELogLevel.LEVEL_WARN
    LogLevel.ERROR -> ELogLevel.LEVEL_ERROR
    LogLevel.FATAL -> ELogLevel.LEVEL_FATAL
}

fun LogModel.toDto(): LogData = LogData(
    androidId = androidId.encodeUtf8(),
    appInstanceId = appInstanceId.encodeUtf8(),
    logLevel = level.toDto(),
    message = message,
    stackTrace = stackTrace,
    serverTimeStamp = timestamp,
    mobileTimeStamp = timestamp,
    versionCode = versionCode,
    userId = userId
)