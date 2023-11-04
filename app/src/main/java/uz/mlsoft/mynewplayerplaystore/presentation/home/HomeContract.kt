package uz.mlsoft.mynewplayerplaystore.presentation.home

import org.orbitmvi.orbit.ContainerHost
import uz.mlsoft.mynewplayerplaystore.data.model.MusicModel
import uz.mlsoft.mynewplayerplaystore.data.model.defaultMusicModel


interface HomeContract {
    interface ScreenModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(event: Event)
    }
    data class UIState(
        val duration: Long = 0L,
        val progress: Float = 0f,
        val progressString: String = "00:00",
        val isAudioPlaying:Boolean = false,
        val audioList:List<MusicModel> = emptyList(),
        val currentMusicModel: MusicModel = defaultMusicModel,
    )

    sealed interface SideEffect {
        object Init : SideEffect
    }

    sealed interface Event {
        object Init : Event
        object LoadAudioData : Event
        object OnStart: Event
        object OnNext: Event
        object OnPrev: Event
        data class OnItemClick(val index:Int):Event
    }



}