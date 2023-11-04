package uz.mlsoft.mynewplayerplaystore.domain

import uz.mlsoft.mynewplayerplaystore.data.model.MusicModel

interface MusicRepository {
    suspend fun getMusicsList(): List<MusicModel>
}