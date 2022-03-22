package com.narcissus.marketplace.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.NavDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSplashBinding
import com.narcissus.marketplace.ui.home.di.HomeDestination
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.qualifier

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var _binding: FragmentSplashBinding? = null

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.launch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashBinding.bind(view)

        splashViewModel.isLaunchedFlow.onEach { isLaunched ->
            if (!isLaunched) return@onEach

            val homeDestination: NavDestination by inject(
                qualifier<HomeDestination>()
            )

            navigator.navigate(homeDestination)
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
