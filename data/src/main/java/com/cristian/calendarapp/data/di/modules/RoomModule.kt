package com.cristian.calendarapp.data.di.modules

import android.content.Context
import androidx.room.Room
import com.cristian.calendarapp.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): LocalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            LocalDatabase::class.java,
            "calendar_app_database"
        ).build()
    }


    @Singleton
    @Provides
    fun provideEventDao(database: LocalDatabase) = database.eventDao


    @Singleton
    @Provides
    fun provideTeamDao(database: LocalDatabase) = database.teamDao


    @Singleton
    @Provides
    fun provideProfileDao(database: LocalDatabase) = database.profileDao
}