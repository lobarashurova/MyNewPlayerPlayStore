package uz.mlsoft.mynewplayerplaystore.presentation.home

import uz.mlsoft.mynewplayerplaystore.navigation.AppNavigator
import uz.mlsoft.mynewplayerplaystore.presentation.play.PlayScreen
import javax.inject.Inject

interface HomeDirection {
    suspend fun moveToPlayScreen()
}

class HomeDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
):HomeDirection{
    override suspend fun moveToPlayScreen() {
        appNavigator.openScreenSaveStack(PlayScreen())
    }

}