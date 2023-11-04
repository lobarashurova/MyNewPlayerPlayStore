package uz.mlsoft.mynewplayerplaystore.presentation.play

import uz.mlsoft.mynewplayerplaystore.navigation.AppNavigator
import javax.inject.Inject

interface PlayScreenDirection {
    suspend fun backToHome()
}

class PlayScreenDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : PlayScreenDirection {
    override suspend fun backToHome() {
        appNavigator.back()
    }
}