package com.narcissus.marketplace.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.onEach

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
            if (isLaunched) {
                findNavController().navigate(R.id.action_fragment_splash_to_fragment_home)
            }
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
