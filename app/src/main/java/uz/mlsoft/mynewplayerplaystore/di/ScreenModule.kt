package uz.mlsoft.mynewplayerplaystore.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import uz.mlsoft.mynewplayerplaystore.presentation.home.HomeViewModel
import uz.mlsoft.mynewplayerplaystore.presentation.splash.SplashViewModel

@Module
@InstallIn(SingletonComponent::class)
interface ScreenModule {

    @[Binds IntoMap ScreenModelKey(SplashViewModel::class)]
    fun bindSplashVM(impl: SplashViewModel): ScreenModel

    @[Binds IntoMap ScreenModelKey(HomeViewModel::class)]
    fun bindHomeVM(imp: HomeViewModel): ScreenModel
}