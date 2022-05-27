package com.app.toalarm.utils

sealed class Resource<T> {
    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(e: Exception, message: String? = e.message) = Error<T>(e, message)
        fun <T> loading() = Loading<T>()
    }

    class Success<T>(val data: T) : Resource<T>()

    class Error<T>(val e: Exception, val message: String?) : Resource<T>()

    class Loading<T> : Resource<T>()
}