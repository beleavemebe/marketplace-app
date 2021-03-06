package com.narcissus.marketplace.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.narcissus.marketplace.core.navigation.destination.HomeDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.Constants
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.ui.splash.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.onEach

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var _binding: FragmentSplashBinding? = null

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        switchTheme()
        splashViewModel.launch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashBinding.bind(view)

        splashViewModel.isLaunchedFlow.onEach { isLaunched ->
            if (!isLaunched) return@onEach
            navigator.navigate(HomeDestination)
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun switchTheme() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref?.getBoolean(Constants.THEME_KEY, false) == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
