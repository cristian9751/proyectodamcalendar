package com.cristian.calendarapp.domain


sealed class DomainError(private val errorMessage : String) : Exception(errorMessage) {
    class InvalidCredential : DomainError(errorMessage = "Invalid credentials")

    class Unauthorized : DomainError(errorMessage = "You are not authorized to access this resource")
    sealed class DuplicatedData(private val name: String ) : DomainError(errorMessage =  "$name already exists") {
        class EmailAlreadyExists() : DuplicatedData("Email")
        class TeamAlreadyExists() : DuplicatedData("Team")
    }
    sealed class InvalidData(val name : String) : DomainError(errorMessage = "$name is invalid") {
        class InvalidEmail() : InvalidData("Email")
    }

    class NotAuthenticated() : DomainError(errorMessage = "User not authenticated")
    class Unexpected() : DomainError(errorMessage = "Unexpected error")

    sealed class NotFound(val name : String) : DomainError(errorMessage = "$name not found") {
        class TeamNotFound() : NotFound("Team")

        class UserNotFound() : NotFound("User")

    }
}