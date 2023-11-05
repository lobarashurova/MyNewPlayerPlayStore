package uz.mlsoft.mynewplayerplaystore.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "musicsTable")
data class MusicEntity (
    val uri: String,
    val displayName: String,
    @PrimaryKey
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String
        )