package com.cristian.calendarapp.data.di.modules

import com.cristian.calendarapp.data.repositoryImpl.AuthenticationRepositoryImpl
import com.cristian.calendarapp.data.repositoryImpl.EventRepositoryImpl
import com.cristian.calendarapp.data.repositoryImpl.RoleRepositoryImpl
import com.cristian.calendarapp.data.repositoryImpl.TeamRepositoryImpl
import com.cristian.calendarapp.data.repositoryImpl.UserRepositoryImpl
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import com.cristian.calendarapp.domain.repository.EventRepository
import com.cristian.calendarapp.domain.repository.RoleRepository
import com.cristian.calendarapp.domain.repository.TeamRepository
import com.cristian.calendarapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class InterfaceModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ) : AuthenticationRepository

    @Binds
    abstract fun bindTeamRepository(
        teamRepositoryImpl: TeamRepositoryImpl
    ) : TeamRepository

    @Binds
    abstract fun bindRoleRepository(
        roleRepositoryImpl: RoleRepositoryImpl
    ) : RoleRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository

    @Binds
    abstract fun bindEventRepository(eventRepositoryImpl : EventRepositoryImpl) : EventRepository
}