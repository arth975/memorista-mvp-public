package com.app.memorista.domain.utils

sealed class BaseResult<T> {

    companion object {
        fun <T> success(data: T): BaseResult<T> = Success(data)
        fun <T> error(e: Exception, message: String? = e.message): BaseResult<T> = Error(e, e.message)
    }

    class Success<T>(val data: T) : BaseResult<T>()

    class Error<T>(val exception: Exception, val message: String?) : BaseResult<T>()
}
