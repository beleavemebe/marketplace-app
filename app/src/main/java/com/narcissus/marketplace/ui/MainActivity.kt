package com.narcissus.marketplace.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.MarketplaceNavigator
import com.narcissus.marketplace.core.navigation.destination.CartDestination
import com.narcissus.marketplace.core.navigation.destination.CatalogDestination
import com.narcissus.marketplace.core.navigation.destination.HomeDestination
import com.narcissus.marketplace.core.navigation.destination.NavDestination
import com.narcissus.marketplace.core.navigation.destination.UserDestination
import com.narcissus.marketplace.core.navigation.destination.uri
import com.narcissus.marketplace.databinding.ActivityMainBinding
import com.narcissus.marketplace.ui.cart.R as CART
import com.narcissus.marketplace.ui.catalog.R as CATALOG
import com.narcissus.marketplace.ui.home.R as HOME
import com.narcissus.marketplace.ui.sign_in.R as SIGN_IN
import com.narcissus.marketplace.ui.splash.R as SPLASH
import com.narcissus.marketplace.ui.user.R as USER

class MainActivity : AppCompatActivity(), MarketplaceNavigator {
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation(navController)
    }

    private val topLevelDestinations =
        setOf(
            HOME.id.nav_graph_home,
            CATALOG.id.nav_graph_catalog,
            CART.id.nav_graph_cart,
            USER.id.nav_graph_user,
        )

    private val fullScreenDestinations =
        setOf(
            SPLASH.id.nav_graph_splash,
            SIGN_IN.id.nav_graph_sign_in,
        )

    private fun initBottomNavigation(navController: NavController) {
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.parent?.id in fullScreenDestinations) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        // TODO: find a proper way
        if (navController.currentDestination?.parent?.id in topLevelDestinations) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun navigate(destination: NavDestination) {
        when (destination) {
            is HomeDestination -> binding.bottomNavigationView.selectedItemId = R.id.nav_graph_home
            is CatalogDestination -> binding.bottomNavigationView.selectedItemId = R.id.nav_graph_catalog
            is CartDestination -> binding.bottomNavigationView.selectedItemId = R.id.nav_graph_cart
            is UserDestination -> binding.bottomNavigationView.selectedItemId = R.id.nav_graph_user
            else -> navController.navigate(destination.uri)
        }
    }

    override fun navigate(destination: NavDestination, options: NavOptions) {
        navController.navigate(destination.uri, options)
    }

    override fun navigate(
        destination: NavDestination,
        options: NavOptions,
        extras: Navigator.Extras,
    ) {
        navController.navigate(destination.uri, options, extras)
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigate(
        destination: NavDestination,
        extras: Navigator.Extras,
    ) {
        navController.navigate(destination.uri, null, extras)
    }
}
