package uz.mlsoft.mynewplayerplaystore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.mynewplayerplaystore.presentation.splash.SplashDirection
import uz.mlsoft.mynewplayerplaystore.presentation.splash.SplashDirectionImpl
import javax.inject.Singleton

/**
 * Created by Shukrullo Zokirov on 10/27/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @Binds
    @Singleton
    fun bindSplashDirection(imp: SplashDirectionImpl): SplashDirection
}