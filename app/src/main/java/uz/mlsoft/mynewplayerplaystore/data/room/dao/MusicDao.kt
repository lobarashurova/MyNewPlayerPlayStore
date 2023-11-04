package uz.mlsoft.mynewplayerplaystore.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mlsoft.mynewplayerplaystore.data.room.entity.MusicEntity


@Dao
interface MusicDao {
    @Query("SELECT * FROM musicsTable")
    fun getAllMusicsList(): List<MusicEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMusics(list: List<MusicEntity>)



}