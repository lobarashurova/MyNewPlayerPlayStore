package uz.mlsoft.mynewplayerplaystore.presentation.splash

import org.orbitmvi.orbit.ContainerHost

interface SplashContract  {
    interface ScreenModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(event: Event)
    }

    data class UIState(
        val progress: Boolean = false
    )

    sealed interface SideEffect {
        object Init : SideEffect
    }

    sealed interface Event {
        object Init : Event
        object Ready : Event
    }
}