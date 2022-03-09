package com.narcissus.marketplace.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _isLaunchedFlow = MutableStateFlow(false)
    val isLaunchedFlow: StateFlow<Boolean> = _isLaunchedFlow.asStateFlow()

    fun launch() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2500L)
            _isLaunchedFlow.value = true
        }
    }
}
