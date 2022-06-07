package com.app.memorista.domain.utils

sealed class ResultOf<T> {

    companion object {
        fun <T> success(data: T): ResultOf<T> = Success(data)
        fun <T> error(e: Exception, message: String? = e.message): ResultOf<T> = Error(e, e.message)
    }

    class Success<T>(val data: T) : ResultOf<T>()

    class Error<T>(val exception: Exception, val message: String?) : ResultOf<T>()
}
