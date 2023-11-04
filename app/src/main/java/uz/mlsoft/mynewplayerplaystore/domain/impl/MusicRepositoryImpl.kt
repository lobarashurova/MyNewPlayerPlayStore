package uz.mlsoft.mynewplayerplaystore.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.mlsoft.mynewplayerplaystore.data.local.content_resolver.ContentResolver
import uz.mlsoft.mynewplayerplaystore.data.model.MusicModel
import uz.mlsoft.mynewplayerplaystore.domain.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : MusicRepository {
    override suspend fun getMusicsList(): List<MusicModel> = withContext(Dispatchers.IO) {
        contentResolver.getMusicData()
    }
}