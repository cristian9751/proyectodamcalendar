package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthenticationRepositoryImpl @Inject constructor(
    private val auth : Auth,
   private val postgrest : Postgrest
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
            }!!


            withContext(Dispatchers.IO) {
               postgrest.rpc(
                   function = "create_user",
                   parameters = mapOf(
                       "p_user_id" to supabaseUser.id,
                       "p_first_name" to user.firstname,
                       "p_last_name" to user.lastname,
                       "p_role" to user.role.toString().lowercase()
                   )
               )
            }



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


        override suspend fun signInWithGoogle(): Result<Boolean> {

        try {
            auth.signInWith(Google)
            return Result.success(true)
        } catch(e : Exception) {
            return Result.failure(DomainError.InvalidCredential())
        }
    }

}