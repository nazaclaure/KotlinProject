package edu.ucb.project.logs.data.service

import com.squareup.wire.GrpcClient

actual fun createGrpcClient(baseUrl: String): GrpcClient {
    return GrpcClient.Builder()
        .baseUrl(baseUrl)
        .build()
}