package edu.ucb.project.logs.data.service

import com.develoop.logs.ELogResultCode
import com.develoop.logs.GrpcLogServiceClient
import com.develoop.logs.LogRequest
import edu.ucb.project.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.project.logs.data.mapper.toDto
import edu.ucb.project.logs.domain.model.LogModel

class LogsService(baseUrl: String) : LogsRemoteDataSource {

    private val client = GrpcLogServiceClient(createGrpcClient(baseUrl))

    override suspend fun send(logs: List<LogModel>): Result<Unit> {
        return try {
            val request = LogRequest(logs = logs.map { it.toDto() })
            val response = client.send().execute(request)
            if (response.resultCode == ELogResultCode.LOG_RESULT_OK) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("El servidor respondio " + response.resultCode))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}