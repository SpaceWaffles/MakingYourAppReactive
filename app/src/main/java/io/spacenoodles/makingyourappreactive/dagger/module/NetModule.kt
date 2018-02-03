package io.spacenoodles.makingyourappreactive.dagger.module

import dagger.Module
import dagger.Provides
import io.spacenoodles.makingyourappreactive.BuildConfig
import io.spacenoodles.makingyourappreactive.dagger.NetInterceptor
import io.spacenoodles.makingyourappreactive.sync.NetService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {
    companion object {
        val BASE_URL = "https://api.imgur.com/3/"
        val OKHTTP_TIMEOUT = 15L //In Seconds
    }
    @Provides
    @Singleton
    internal fun provideNetInterceptor(): NetInterceptor {
        return NetInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideHttpClient(interceptor: NetInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

        try {
            if(BuildConfig.DEBUG) {
                val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor()
                loggingInterceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }

        return builder.build()
    }

    @Provides
    internal fun provideNetService(okHttpClient: OkHttpClient, interceptor: NetInterceptor): NetService {

        val tokenClient = okHttpClient.newBuilder()
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(tokenClient)
                .build()
        return retrofit.create(NetService::class.java)
    }
}