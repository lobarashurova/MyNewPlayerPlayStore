package uz.mlsoft.mynewplayerplaystore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.mynewplayerplaystore.domain.MusicRepository
import uz.mlsoft.mynewplayerplaystore.domain.impl.MusicRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindMusicRepository(impl: MusicRepositoryImpl): MusicRepository
}