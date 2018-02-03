package io.spacenoodles.makingyourappreactive.dagger

import okhttp3.Interceptor
import okhttp3.Response

class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val ongoing = chain.request().newBuilder()

        ongoing.header("Authorization", "Client-ID YOUR_ID_HERE")
        ongoing.addHeader("Content-Type", "application/json")

        return chain.proceed(ongoing.build())
    }
}