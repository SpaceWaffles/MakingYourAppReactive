package io.spacenoodles.makingyourappreactive.dagger

import okhttp3.Interceptor
import okhttp3.Response

class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val ongoing = chain.request().newBuilder()

        ongoing.header("Authorization", "Client-ID 37398025a821c1b")
        ongoing.addHeader("Content-Type", "application/json")

        return chain.proceed(ongoing.build())
    }
}