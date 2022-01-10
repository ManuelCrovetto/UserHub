package com.example.userhub.ui.activity.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.userhub.R
import com.example.userhub.core.ex.lostConnectionSnackBar
import com.example.userhub.core.ex.reconnectedSnackBar
import com.example.userhub.databinding.ActivityMainBinding
import com.example.userhub.ui.activity.viewmodel.MainActivityViewModel
import com.example.userhub.ui.factory.UsersHubFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var fragmentFactory: UsersHubFragmentFactory

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val fragmentHost: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    private var isConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        supportFragmentManager.fragmentFactory = fragmentFactory
        setUpUI()
        setUpNavigation()
        initNavigationListener()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            connectionStatus.observe(this@MainActivity) { status ->
                with(binding) {
                    isConnected = if (!status){
                        root.lostConnectionSnackBar()
                        false
                    } else {
                        if (!isConnected)
                            root.reconnectedSnackBar()
                            true
                    }
                }
            }
        }
    }

    private fun setUpUI() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            setSupportActionBar(materialToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            toolbarShadow.bringToFront()
        }
    }

    private fun setUpNavigation() {
        navController = fragmentHost.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.usersOverViewFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when {
                destination.id != R.id.usersOverViewFragment -> binding.materialToolbar.setNavigationIcon(
                    R.drawable.ic_baseline_chevron_left_24
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}