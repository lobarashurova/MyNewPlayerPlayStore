package uz.mlsoft.mynewplayerplaystore.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.mynewplayerplaystore.data.room.dao.MusicDao
import uz.mlsoft.mynewplayerplaystore.data.room.database.MusicDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(context, MusicDatabase::class.java, "music.db")
            .allowMainThreadQueries()
            .build()
    }


    @[Provides Singleton]
    fun provideDao(database: MusicDatabase): MusicDao = database.getMusicDao()

}