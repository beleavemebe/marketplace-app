package com.narcissus.marketplace.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.GetOrderList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel : ViewModel(),KoinComponent {
    private val _isLaunchedFlow = MutableStateFlow(false)
    val isLaunchedFlow: StateFlow<Boolean> = _isLaunchedFlow.asStateFlow()
    val useCase:GetOrderList by inject()
    fun launch() {
        viewModelScope.launch {
            useCase().collect {
                Log.d("DEBUG",it.toString())
            }
            delay(1000L)
            _isLaunchedFlow.value = true
        }
    }
}
