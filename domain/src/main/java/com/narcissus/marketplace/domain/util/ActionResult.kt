package com.narcissus.marketplace.domain.util

typealias Mapper<Input, Output> = (Input) -> Output

sealed class ActionResult<T> {
    class SuccessResult<T>(val data: T) : ActionResult<T>()
    class ErrorResult<T>(val message: String) : ActionResult<T>()

    fun <R> mapResult(mapper: Mapper<T, R>) = when (this) {
        is SuccessResult -> SuccessResult(mapper(this.data))
        is ErrorResult -> ErrorResult(this.message)
    }

    fun getOrThrow(): T = when (this) {
        is ErrorResult -> error("Result is error")
        is SuccessResult -> this.data
    }
}
