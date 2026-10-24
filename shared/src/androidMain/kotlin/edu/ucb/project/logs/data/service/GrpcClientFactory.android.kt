package edu.ucb.project.logs.data.service

import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol

actual fun createGrpcClient(baseUrl: String): GrpcClient {
    val builder = OkHttpClient.Builder()
    // Sin HTTPS (emulador -> PC) hay que forzar HTTP/2; con HTTPS (ngrok) se negocia solo
    if (baseUrl.startsWith("http://")) {
        builder.protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE))
    }
    return GrpcClient.Builder()
        .client(builder.build())
        .baseUrl(baseUrl)
        .build()
}