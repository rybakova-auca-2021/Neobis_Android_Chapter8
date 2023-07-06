package com.example.neobis_android_chapter8

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.neobis_android_chapter8.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar_view)
        bottomNavigationView.setupWithNavController(navController)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            navController.navigate(R.id.addProductFragment)
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.menu_item5 -> {
                    navController.navigate(R.id.mainPageFragment)
                    true
                }
                else -> false
            }
        }
    }

    fun hideBtmNav() {
        val navBar = findViewById<View>(R.id.nav_bar)
        val floatBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        navBar.visibility = View.GONE
        floatBtn.visibility = View.GONE
    }

    fun showBtmNav() {
        val navBar = findViewById<View>(R.id.nav_bar)
        val floatBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        navBar.visibility = View.VISIBLE
        floatBtn.visibility = View.VISIBLE
    }
}