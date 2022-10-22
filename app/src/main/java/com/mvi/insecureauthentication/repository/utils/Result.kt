package com.mvi.insecureauthentication.repository.utils

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    @PublishedApi
    internal var isResultHandled = false

    inline fun whenSuccess(block: Success<T>.() -> Unit): Result<T> {
        if (this is Success) {
            this.block()
            isResultHandled = true
        }
        return this
    }

    inline fun whenError(block: Error.() -> Unit): Result<T> {
        if (!isResultHandled && this is Error) {
            this.block()
            isResultHandled = true
        }
        return this
    }
}