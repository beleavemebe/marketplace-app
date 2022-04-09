package com.narcissus.marketplace.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Err> : ViewModel() {

    abstract fun mapThrowable(t: Throwable): Err

    private val _errors = MutableSharedFlow<Err>(replay = 1)
    val errors = _errors.asSharedFlow()

    fun execute(block: suspend () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            block()
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            val err = mapThrowable(throwable)
            _errors.emit(err)
        }
    }
}
