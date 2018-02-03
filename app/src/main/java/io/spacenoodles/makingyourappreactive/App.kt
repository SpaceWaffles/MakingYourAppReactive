package io.spacenoodles.makingyourappreactive

import android.app.Application
import io.spacenoodles.makingyourappreactive.dagger.component.AppComponent
import io.spacenoodles.makingyourappreactive.dagger.component.DaggerAppComponent
import io.spacenoodles.makingyourappreactive.dagger.module.AppModule

class App : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
                    .builder()
                    .appModule(AppModule(this))
                    .build()
    }
}