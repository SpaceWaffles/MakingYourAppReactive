package io.spacenoodles.makingyourappreactive.dagger.component

import dagger.Component
import io.spacenoodles.makingyourappreactive.dagger.module.AppModule
import io.spacenoodles.makingyourappreactive.dagger.module.NetModule
import io.spacenoodles.makingyourappreactive.dagger.module.RepositoryModule
import io.spacenoodles.makingyourappreactive.viewModel.MainActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetModule::class,
        RepositoryModule::class)
) interface AppComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
}