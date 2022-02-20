package com.narcissus.marketplace.util

typealias Mapper<Input, Output> = (Input) -> Output
sealed class Result<T> {
    class SuccsessResult<T>(val data: T) : Result<T>()
    class ErrorResult<T>(val message: String) : Result<T>()

    fun <R> mapResult(mapper: Mapper<T, R>) = when (this) {
        is SuccsessResult -> SuccsessResult(mapper(this.data))
        is ErrorResult -> ErrorResult(this.message)
    }

}