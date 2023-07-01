package com.slowAndSteady.slowdy.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.R
import com.slowAndSteady.slowdy.databinding.ActivityMainBinding
import com.slowAndSteady.slowdy.databinding.FragmentSigninBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_navigation_graph) as NavHostFragment
         navController = navHostFragment.navController

        binding.bottomNav.setOnItemSelectedListener {
            val id = it.itemId
            navigate(id)
            true
        }
    }

    private fun navigate(menuId: Int) {
        when(menuId) {
            R.id.home_bottom_navigation -> {
                navController.navigate(R.id.homeFragment)
            }
            R.id.notification_bottom_navigation ->{
                navController.navigate(R.id.notificationsFragment)
            }
            R.id.settings_bottom_navigation ->{
                navController.navigate(R.id.settingsFragment)
            }
        }
    }
}