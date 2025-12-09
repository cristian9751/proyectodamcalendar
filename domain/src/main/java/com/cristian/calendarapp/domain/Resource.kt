package com.cristian.calendarapp.domain


sealed class Resource<T>(val data: T? = null, val domainError : Exception? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(error: Exception, data: T? = null): Resource<T>(data, error)
}