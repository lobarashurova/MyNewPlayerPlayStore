package uz.mlsoft.mynewplayerplaystore.data.local.content_resolver

import uz.mlsoft.mynewplayerplaystore.data.model.MusicModel

interface ContentResolver {
    suspend fun getMusicData(): List<MusicModel>
}