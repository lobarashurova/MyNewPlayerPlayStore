package uz.mlsoft.mynewplayerplaystore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.mynewplayerplaystore.navigation.AppNavigator
import uz.mlsoft.mynewplayerplaystore.navigation.NavigationDispatcher
import uz.mlsoft.mynewplayerplaystore.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindNavigationHandler(imp: NavigationDispatcher): NavigationHandler

    @Binds
    fun bindAppNavigator(imp: NavigationDispatcher): AppNavigator
}