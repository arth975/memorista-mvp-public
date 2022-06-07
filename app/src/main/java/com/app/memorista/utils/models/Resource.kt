package com.app.memorista.utils.models

sealed class Resource<T>(val data: T?) {
    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(e: Exception, message: String? = e.message) = Error<T>(e, message)
        fun <T> loading() = Loading<T>()
        fun <T> empty() = Empty<T>()
    }

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(val e: Exception, val message: String?) : Resource<T>(null)

    class Loading<T> : Resource<T>(null)

    class Empty<T> : Resource<T>(null)
}