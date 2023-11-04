package uz.mlsoft.mynewplayerplaystore.player.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.mlsoft.mynewplayerplaystore.R
import javax.inject.Inject


private const val NOTIFICATION_ID = 101
private const val NOTIFICATION_CHANNEL_NAME = "notificationChannelName"
private const val NOTIFICATION_CHANNEL_ID = "notificationChannelId"

class AudioNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val exoPlayer: ExoPlayer
) {
    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @UnstableApi
    fun startNotificationService(
        mediaSession: MediaSession,
        mediaSessionService: MediaSessionService
    ) {
        buildNotification(mediaSession)
        startForegroundService(mediaSessionService)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startForegroundService(mediaSessionService: MediaSessionService) {
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        mediaSessionService.startForeground(NOTIFICATION_ID, notification)
    }

    @UnstableApi
    private fun buildNotification(mediaSession: MediaSession) {
        PlayerNotificationManager.Builder(
            context,
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID
        ).setMediaDescriptionAdapter(
            AudioNotificationAdapter(
                context,
                pendingIntent = mediaSession.sessionActivity
            )
        )
            .setSmallIconResourceId(R.drawable.music_place)
            .build()
            .also {
                it.setMediaSessionToken(mediaSession.sessionCompatToken)
                it.setUseFastForwardActionInCompactView(true)
                it.setUseRewindActionInCompactView(true)
                it.setUseNextActionInCompactView(true)
                it.setPriority(NotificationCompat.PRIORITY_HIGH)
                it.setPlayer(exoPlayer)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }
}