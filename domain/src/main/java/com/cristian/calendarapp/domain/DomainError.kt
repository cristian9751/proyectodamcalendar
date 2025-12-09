package com.cristian.calendarapp.domain


sealed class DomainError(private val errorMessage : String) : Exception(errorMessage) {
    class InvalidCredential : DomainError(errorMessage = "Invalid credentials")
    sealed class DuplicatedData(private val name: String ) : DomainError(errorMessage =  "$name already exists") {
        class EmailAlreadyExists() : DuplicatedData("Email")
    }
    sealed class InvalidData(val name : String) : DomainError(errorMessage = "$name is invalid") {
        class InvalidEmail() : InvalidData("Email")
    }
    class Unexpected() : DomainError(errorMessage = "Unexpected error")
}