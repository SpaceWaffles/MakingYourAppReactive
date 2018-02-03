package io.spacenoodles.makingyourappreactive.dagger.module

import dagger.Module
import dagger.Provides
import io.spacenoodles.makingyourappreactive.sync.NetService
import io.spacenoodles.makingyourappreactive.sync.repository.ImagePostRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    internal fun provideImagePostRepository(netService: NetService): ImagePostRepository {
        return ImagePostRepository(netService)
    }
}
