package com.narcissus.marketplace.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.MarketplaceCrossModuleNavigator
import com.narcissus.marketplace.core.navigation.destination.NavDestination
import com.narcissus.marketplace.core.navigation.destination.uri
import com.narcissus.marketplace.databinding.ActivityMainBinding
import com.narcissus.marketplace.ui.checkout.CheckoutForegroundWorker
import com.narcissus.marketplace.ui.checkout.PayIntentFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), MarketplaceCrossModuleNavigator, KoinComponent {
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }


    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .build()
    val data = Data.Builder().putString("Order UUID","FFFF").putString("ResultKey","rs1")
    val data2 = Data.Builder().putString("Order UUID","DDDD").putInt("NotificationId",2).putString("ResultKey","rs2")
    val workRequest = OneTimeWorkRequest.Builder(CheckoutForegroundWorker::class.java)
        .setConstraints(constraints)
        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        .setInputData(data.build()).build()
    val workRequest2 = OneTimeWorkRequest.Builder(CheckoutForegroundWorker::class.java)
        .setConstraints(constraints)
        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        .setInputData(data2.build()).build()
    val workManager = WorkManager.getInstance(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation(navController)




        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this) { workInfo: WorkInfo? ->
                Log.d("DEBUG","WORKER OBSERVED: $workInfo.toString()" )
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> Log.d(
                            "DEBUG",
                            "WORKER RETURNED SUCCESS RESULT",
                        )
                        WorkInfo.State.FAILED -> workInfo.outputData.getString("checkout result message")
                            ?.let { Log.d("DEBUG", "WORKER FAILED: $it") }
                        WorkInfo.State.RUNNING ->
                        Log.d(
                            "DEBUG",
                            "WORKER RUNNING",
                        )
                        else -> {}
                    }
                }
            }
        Log.d("DEBUG","BEFORE ENQUEUE")
        workManager.enqueue(workRequest)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            workManager.enqueue(workRequest2)
            sendBroadcast(Intent(PayIntentFilter.PAY_INTENT_FILTER).putExtra("FFFF",true))
        }
    }

    private val fullScreenDestinations =
        setOf(
            com.narcissus.marketplace.ui.splash.R.id.nav_graph_splash,
            R.id.nav_graph_sign_in,
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

    private val topLevelDestinations =
        setOf(
            R.id.nav_graph_home,
            R.id.nav_graph_catalog,
            R.id.nav_graph_cart,
            com.narcissus.marketplace.ui.user.R.id.nav_graph_user,
        )

    override fun onBackPressed() {
        // TODO: find a proper way
        if (navController.currentDestination?.parent?.id in topLevelDestinations) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun navigate(destination: NavDestination) {
        navController.navigate(destination.uri)
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

    override fun navigate(
        destination: NavDestination,
        extras: Navigator.Extras,
    ) {
        navController.navigate(destination.uri, null, extras)
    }
}
