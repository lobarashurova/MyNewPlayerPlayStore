package uz.mlsoft.mynewplayerplaystore.presentation.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.hilt.ScreenModelKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import javax.inject.Inject



class SplashViewModel @Inject constructor(
    private val direction: SplashDirection
) : ScreenModel, SplashContract.ScreenModel {
    init {
        coroutineScope.launch {
            delay(2000)
            direction.moveToHomeScreen()
        }
    }

    override fun onEventDispatcher(event: SplashContract.Event) {

    }

    override val container =
        coroutineScope.container<SplashContract.UIState, SplashContract.SideEffect>(
            SplashContract.UIState()
        )
}