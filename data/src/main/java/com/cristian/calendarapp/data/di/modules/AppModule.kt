package com.cristian.calendarapp.data.di.modules

import com.cristian.calendarapp.data.repositoryImpl.AuthenticationRepositoryImpl
import com.cristian.calendarapp.data.repositoryImpl.TeamRepositoryImpl
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import com.cristian.calendarapp.domain.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
abstract  class  AppModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ) : AuthenticationRepository

    @Binds
    abstract fun bindTeamRepository(
        teamRepositoryImpl: TeamRepositoryImpl
    ) : TeamRepository

}