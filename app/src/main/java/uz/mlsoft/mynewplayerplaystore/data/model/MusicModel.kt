package uz.mlsoft.mynewplayerplaystore.data.model

import android.net.Uri
import androidx.core.net.toUri

data class MusicModel(
    val uri: Uri,
    val displayName: String,
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String
)

val defaultMusicModel = MusicModel(
    "".toUri(), "", 0L, "", "", 0, ""
)