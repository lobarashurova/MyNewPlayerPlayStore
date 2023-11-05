package uz.mlsoft.mynewplayerplaystore.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import uz.mlsoft.mynewplayerplaystore.domain.MusicRepository
import uz.mlsoft.mynewplayerplaystore.player.service.AudioService
import uz.mlsoft.mynewplayerplaystore.player.service.AudioServiceHandler
import uz.mlsoft.mynewplayerplaystore.player.service.AudioState
import uz.mlsoft.mynewplayerplaystore.player.service.PlayerEvent
import uz.mlsoft.mynewplayerplaystore.utils.formatDuration
import uz.mlsoft.mynewplayerplaystore.utils.myTimber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val audioServiceHandler: AudioServiceHandler,
    private val repository: MusicRepository,
    @ApplicationContext private val context: Context
) : ScreenModel, HomeContract.ScreenModel {
    private var isServiceRunning = false

    init {
        audioServiceHandler.audioState.onEach { mediaState ->
            myTimber("init")
            when (mediaState) {
                AudioState.Initial -> {}
                is AudioState.Buffering -> calculateProgressValue(mediaState.progress)
                is AudioState.Playing -> intent {
                    reduce { this.state.copy(isAudioPlaying = mediaState.isPlaying) }
                }

                is AudioState.Progress -> calculateProgressValue(mediaState.progress)
                is AudioState.CurrentPlaying -> intent {
                    reduce {
                        this.state.copy(
                            currentMusicModel =
                            container.stateFlow.value.audioList[mediaState.mediaItemIndex]
                        )
                    }
                }

                is AudioState.Ready -> intent {
                    reduce {
                        this.state.copy(duration = mediaState.duration)
                    }
                }
            }
            myTimber("size repository:${repository.getMusicsList().size}")
        }.launchIn(coroutineScope)
    }


    override fun onEventDispatcher(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.LoadAudioData -> loadAudioData()
            HomeContract.Event.OnPrev -> audioServiceHandler
                .onPlayerEvents(PlayerEvent.SeekToPrev)

            HomeContract.Event.OnNext -> audioServiceHandler
                .onPlayerEvents(PlayerEvent.SeekToNext)

            HomeContract.Event.OnStart -> audioServiceHandler
                .onPlayerEvents(PlayerEvent.PlayPause)

            is HomeContract.Event.OnItemClick -> selectMusic(event.index)

            else -> {}
        }
    }

    override val container = coroutineScope
        .container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState())


    private fun selectMusic(index: Int) {
        audioServiceHandler.onPlayerEvents(
            PlayerEvent.SelectedAudioChange,
            selectedAudioIndex = index
        )
        startService()
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(context, AudioService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            isServiceRunning = true
        }
    }

    override fun onDispose() {
        coroutineScope.launch {
            audioServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onDispose()
    }

    private fun loadAudioData() = intent {
        val audio = repository.getMusicsList()
        myTimber("audio size:${audio.size}")
        reduce { this.state.copy(audioList = audio) }
        setMediaItems()
    }

    private fun setMediaItems() {
        container.stateFlow.value.audioList.map { audio ->
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.displayName)
                        .build()
                ).build()
        }.also { audioServiceHandler.setMediaItemList(it) }

    }

    private fun calculateProgressValue(currentProgress: Long) = intent {
        reduce {
            this.state.copy(
                progressString = formatDuration(currentProgress),
                progress = if (currentProgress > 0) ((currentProgress.toFloat() / container.stateFlow.value.duration.toFloat()) * 100f) else 0f
            )
        }
    }
}
