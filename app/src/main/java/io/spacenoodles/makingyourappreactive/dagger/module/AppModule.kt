package io.spacenoodles.makingyourappreactive.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(internal val context: Context) {
    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }
}