package com.cristian.calendarapp.data.di.modules

import com.cristian.calendarapp.data.repositoryImpl.AuthenticationRepositoryImpl
import com.cristian.calendarapp.data.repositoryImpl.TeamRepositoryImpl
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import com.cristian.calendarapp.domain.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideCoroutineDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(
        coroutineDispatcher: CoroutineDispatcher
    ) : CoroutineScope {
        return CoroutineScope(SupervisorJob() + coroutineDispatcher)

    }

}