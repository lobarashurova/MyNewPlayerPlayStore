package uz.mlsoft.mynewplayerplaystore.player.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AudioServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer
) : Player.Listener {
    private val _audioState: MutableStateFlow<AudioState> =
        MutableStateFlow(AudioState.Initial)
    val audioState: StateFlow<AudioState> = _audioState.asStateFlow()

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    init { exoPlayer.addListener(this) }

    fun setMediaItemList(mediaItems: List<MediaItem>) {
        scope.launch {
            exoPlayer.setMediaItems(mediaItems)
            exoPlayer.prepare()
        }
    }

    fun onPlayerEvents(
        playerEvent: PlayerEvent, selectedAudioIndex: Int = -1,
        seekPosition: Long = 0
    ) {
        scope.launch {
            when (playerEvent) {
                PlayerEvent.Backward -> exoPlayer.seekBack()
                PlayerEvent.Forward -> exoPlayer.seekForward()
                PlayerEvent.SeekToNext -> exoPlayer.seekToNext()
                PlayerEvent.SeekToPrev -> exoPlayer.seekToPrevious()
                PlayerEvent.PlayPause -> playOrPause()
                PlayerEvent.SeekTo -> exoPlayer.seekTo(seekPosition)
                PlayerEvent.SelectedAudioChange -> {
                    when (selectedAudioIndex) {
                        exoPlayer.currentMediaItemIndex -> {
                            playOrPause() }
                        else -> {
                            exoPlayer.seekToDefaultPosition(selectedAudioIndex)
                            _audioState.value = AudioState.Playing(isPlaying = true)
                            exoPlayer.playWhenReady = true
                            startProgressUpdate()
                        }
                    }
                }

                PlayerEvent.Stop -> stopProgressUpdate()
                is PlayerEvent.UpdateProgress -> {
                    exoPlayer.seekTo(
                        (exoPlayer.duration * playerEvent.newProgress).toLong()
                    )
                }
            }
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _audioState.value =
                AudioState.Buffering(exoPlayer.currentPosition)

            ExoPlayer.STATE_READY -> _audioState.value =
                AudioState.Ready(exoPlayer.duration)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _audioState.value = AudioState.Playing(isPlaying = isPlaying)
        _audioState.value = AudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)
        if (isPlaying) {
            scope.launch {
                startProgressUpdate()
            }
        } else {
            stopProgressUpdate()
        }
    }

    private suspend fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()
        } else {
            exoPlayer.play()
            _audioState.value = AudioState.Playing(
                isPlaying = true
            )
            startProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _audioState.value =
                AudioState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _audioState.value = AudioState.Playing(isPlaying = false)
    }
}

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object SelectedAudioChange : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object SeekToPrev : PlayerEvent()
    object SeekToNext : PlayerEvent()
    object SeekTo : PlayerEvent()
    object Stop : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}

sealed class AudioState {
    object Initial : AudioState()
    data class Ready(val duration: Long) : AudioState()
    data class Progress(val progress: Long) : AudioState()
    data class Buffering(val progress: Long) : AudioState()
    data class Playing(val isPlaying: Boolean) : AudioState()
    data class CurrentPlaying(val mediaItemIndex: Int) : AudioState()
}