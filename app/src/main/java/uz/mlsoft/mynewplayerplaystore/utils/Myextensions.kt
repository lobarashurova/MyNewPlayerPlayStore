package uz.mlsoft.mynewplayerplaystore.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import timber.log.Timber
import uz.mlsoft.mynewplayerplaystore.R
import java.util.concurrent.TimeUnit
import kotlin.math.floor

fun myTimber(message: String) {
    Timber.tag("TTT").d(message)
}

fun getAlbumArt(context: Context, uri: Uri): Bitmap {
    myTimber("uri:$uri")
    val mmr = MediaMetadataRetriever()
    try {
        mmr.setDataSource(context, uri)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val data = mmr.embeddedPicture
    return if (data != null) {
        BitmapFactory.decodeByteArray(data, 0, data.size)
    } else {
        BitmapFactory.decodeResource(context.resources, R.drawable.my_music)
    }
}

fun timeStampToDuration(position: Long): String {
    val totalSecond = floor(position / 1E3).toInt()
    val minutes = totalSecond / 60
    val remainingSeconds = totalSecond - (minutes * 60)

    return if (position < 0) "--:--"
    else "%d:%02d".format(minutes, remainingSeconds)
}


fun formatDuration(duration: Long): String {
    val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MICROSECONDS)
    val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)

    return String.format("%02d:%02d", minute, seconds)

}