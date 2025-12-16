package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.data.SupabaseSync
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject


class AuthenticationRepositoryImpl @Inject constructor(
    private val auth : Auth,
    private val supabaseSync: SupabaseSync
) : AuthenticationRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<String> {
        try {
            auth.signInWith(provider = Email) {
                this.email = email
                this.password = password
            }

            auth.currentUserOrNull()?.let { user ->
                supabaseSync(user.id)
            }

        } catch (e: Exception) {
            return if(e is AuthRestException && e.error == AuthErrorCode.InvalidCredentials.value) {
                Result.failure(exception = DomainError.InvalidCredential())
            } else {
                Result.failure(exception = DomainError.Unexpected())
            }
        }

        return Result.success(auth.currentUserOrNull()?.id ?: "")
    }

    override suspend fun signUp(user: User): Result<String> {
        try {
            val supabaseUser = auth.signUpWith(provider = Email) {
                this.email = user.email
                this.password = user.password
                this.data = buildJsonObject {
                    put("first_name", user.firstname)
                    put("last_name", user.lastname)
                    put("role", user.role.name.lowercase())
                }
            }!!


            return Result.success(supabaseUser.id)
        } catch (e: Exception) {
            return if(e is AuthRestException) {
                    when(e.error) {
                        AuthErrorCode.UserAlreadyExists.value ->
                            Result.failure(exception = DomainError.DuplicatedData.EmailAlreadyExists())

                        AuthErrorCode.EmailAddressInvalid.value ->
                            Result.failure(exception = DomainError.InvalidData.InvalidEmail())

                        else ->
                            Result.failure(exception = DomainError.Unexpected())
                    }
            } else {
                Result.failure(exception = DomainError.Unexpected())
            }
        }
    }

     override suspend fun findAuthenticatedUserId() : Result<String> {
        val sessionStatus = auth.sessionStatus
            .filter { sessionStatus -> sessionStatus !is SessionStatus.Initializing }
            .map { sessionStatus ->
                when(sessionStatus) {
                    is SessionStatus.Authenticated -> {
                        supabaseSync(sessionStatus.session.user?.id!!)
                        Result.success(sessionStatus.session.user?.id!!)

                    }

                    is SessionStatus.NotAuthenticated -> {
                        Result.failure(exception = DomainError.NotAuthenticated())
                    }

                    is SessionStatus.RefreshFailure -> {
                        Result.failure(exception = DomainError.NotAuthenticated())
                    }

                    else -> {
                        Result.failure(exception = DomainError.Unexpected())
                    }
                }
            }.flowOn(Dispatchers.IO)

         return sessionStatus.first()
    }

}