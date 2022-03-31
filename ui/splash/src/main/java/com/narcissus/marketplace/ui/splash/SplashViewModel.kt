package com.narcissus.marketplace.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.core.navigation.destination.HomeDestination
import com.narcissus.marketplace.core.navigation.destination.MarketplaceUnavailableDestination
import com.narcissus.marketplace.core.navigation.destination.NavDestination
import com.narcissus.marketplace.domain.usecase.GetApiStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel(
    private val getApiStatus: GetApiStatus,
) : ViewModel(), KoinComponent {
    private val _destinationFlow = MutableSharedFlow<NavDestination>(replay = 1)
    val destinationFlow = _destinationFlow.asSharedFlow()

    fun launch() {
        viewModelScope.launch {
            launch {
                val apiStatus = getApiStatus()
                if (apiStatus.isAlive) {
                    val home: HomeDestination by inject()
                    _destinationFlow.emit(home)
                }
            }

            delay(5000L)
            val marketplaceUnavailable: MarketplaceUnavailableDestination by inject()
            _destinationFlow.emit(marketplaceUnavailable)
        }
    }
}
