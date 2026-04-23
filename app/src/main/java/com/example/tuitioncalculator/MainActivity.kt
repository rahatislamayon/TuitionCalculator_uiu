package com.example.tuitioncalculator

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.tuitioncalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // Reusable NavOptions with smooth transitions for forward navigation
    private val forwardNavOptions by lazy {
        NavOptions.Builder()
            .setEnterAnim(R.anim.nav_fade_in)
            .setExitAnim(R.anim.nav_fade_out)
            .setPopEnterAnim(R.anim.nav_pop_enter)
            .setPopExitAnim(R.anim.nav_pop_exit)
            .setLaunchSingleTop(true)
            .setPopUpTo(R.id.homeFragment, false)
            .build()
    }

    // NavOptions for going BACK to Home — reverse direction animations
    private val backToHomeNavOptions by lazy {
        NavOptions.Builder()
            .setEnterAnim(R.anim.nav_pop_enter)
            .setExitAnim(R.anim.nav_pop_exit)
            .setPopEnterAnim(R.anim.nav_pop_enter)
            .setPopExitAnim(R.anim.nav_pop_exit)
            .setLaunchSingleTop(true)
            .setPopUpTo(R.id.homeFragment, true)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Wire up bottom nav with custom transition animations
        setupBottomNavigation()

        // Keep the bottom nav indicator in sync when back-stack changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val menu = binding.bottomNavigation.menu
            for (i in 0 until menu.size()) {
                val item = menu.getItem(i)
                if (item.itemId == destination.id) {
                    if (!item.isChecked) {
                        // Temporarily remove listener to avoid recursive navigation
                        binding.bottomNavigation.setOnItemSelectedListener(null)
                        item.isChecked = true
                        setupBottomNavigation()
                    }
                    break
                }
            }
        }

        // Handle device back button: if not on Home, go to Home with backward animation
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDest = navController.currentDestination?.id
                if (currentDest != R.id.homeFragment) {
                    // Navigate back to Home with reverse transition
                    try {
                        navController.navigate(R.id.homeFragment, null, backToHomeNavOptions)
                    } catch (e: IllegalArgumentException) {
                        binding.bottomNavigation.selectedItemId = R.id.homeFragment
                    }
                } else {
                    // On Home already — let the system default handle it (exit)
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            // Avoid re-navigating to the current destination
            if (navController.currentDestination?.id == item.itemId) {
                return@setOnItemSelectedListener true
            }

            // Use backward animation when going Home, forward animation otherwise
            val options = if (item.itemId == R.id.homeFragment) {
                backToHomeNavOptions
            } else {
                forwardNavOptions
            }

            try {
                navController.navigate(item.itemId, null, options)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }
}
