package com.narcissus.marketplace.ui.user.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.ui.user.composables.Loading
import com.narcissus.marketplace.ui.user.theme.DefaultTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class OrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val viewModel: OrdersViewModel = getViewModel()
            DefaultTheme {
                OrdersScreenContent(viewModel)
            }
        }
    }

    @Composable
    private fun OrdersScreenContent(viewModel: OrdersViewModel) {
        viewModel.collectSideEffect(::handleSideEffect)
        val state = viewModel.collectAsState().value
        when {
            state.isLoading ->
                Loading()

            state.orders.isEmpty() ->
                NoOrdersYet()

            else ->
                OrdersScreen(viewModel, state.orders)
        }
    }

    private fun handleSideEffect(sideEffect: OrdersSideEffect) {
        when (sideEffect) {
            OrdersSideEffect.NavigateUp -> navigator.navigateUp()
        }
    }
}
