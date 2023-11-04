package uz.mlsoft.mynewplayerplaystore.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.mlsoft.mynewplayerplaystore.data.room.dao.MusicDao
import uz.mlsoft.mynewplayerplaystore.data.room.entity.MusicEntity

@Database(entities = [MusicEntity::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun getMusicDao():MusicDao
}