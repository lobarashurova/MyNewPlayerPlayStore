package uz.mlsoft.mynewplayerplaystore.data.local.content_resolver.impl

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.mlsoft.mynewplayerplaystore.data.local.content_resolver.ContentResolver
import uz.mlsoft.mynewplayerplaystore.data.model.MusicModel
import uz.mlsoft.mynewplayerplaystore.utils.myTimber
import javax.inject.Inject

class ContentResolverImpl
@Inject constructor(@ApplicationContext val context: Context) :
    ContentResolver {
    private var myCursor: Cursor? = null

    private val projection: Array<String> = arrayOf(
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.AudioColumns.TITLE,
    )
    private val selectionClause: String = "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?"

    private val selectionArg = arrayOf("1")
    private val sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC"

    override suspend fun getMusicData(): List<MusicModel> {
        myTimber("getCursorData() size ${getCursorData().size}")
        return getCursorData()

    }

    private fun getCursorData(): MutableList<MusicModel> {
        val audioList = mutableListOf<MusicModel>()

        myCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selectionClause,
            selectionArg,
            sortOrder
        )
        myCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val artistNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)

            cursor.apply {
                if (count == 0) {
                    myTimber("musics are not available")
                } else {
                    while (cursor.moveToNext()) {
                        val displayName = getString(displayNameColumn)
                        val id = getLong(idColumn)
                        myTimber("content id:$id")
                        val artistName = getString(artistNameColumn)
                        val data = getString(dataColumn)
                        val duration = getInt(durationColumn)
                        val title = getString(titleColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        myTimber("uri content:${uri}")//why i am getting empty uri
                        myTimber("id:${getLong(idColumn)}")
                        audioList += MusicModel(
                            uri,
                            displayName,
                            id,
                            artistName,
                            data,
                            duration,
                            title
                        )
                    }
                }
            }
        }
        return audioList
    }
}