package uz.mlsoft.mynewplayerplaystore.presentation.splash

import uz.mlsoft.mynewplayerplaystore.navigation.AppNavigator
import uz.mlsoft.mynewplayerplaystore.presentation.home.HomeScreen
import javax.inject.Inject

interface SplashDirection {
    suspend fun moveToHomeScreen()
}

class SplashDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SplashDirection {
    override suspend fun moveToHomeScreen() {
        appNavigator.openScreenWithoutSavingToStack(HomeScreen())
    }

}