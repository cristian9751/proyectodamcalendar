package com.cristian.calendarapp.domain


sealed class DomainError(private val exception: Exception) {
    class InvalidCredential : DomainError(exception = Exception("Invalid credentials"))
    class DuplicatedData(val name : String) : DomainError(exception = Exception("$name already exists"))
    class InvalidData(val name : String) : DomainError(exception = Exception("$name is invalid"))
    class Unexpected() : DomainError(exception = Exception("Domain Error"))

    fun getException() : Exception {
        return this.exception
    }
}