package com.example.neobis_android_chapter8

import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.neobis_android_chapter8.databinding.ActivityHomeBinding
import com.example.neobis_android_chapter8.view.products.MainPageFragment
import com.example.neobis_android_chapter8.view.profile.ProfileFragment
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

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
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
    fun hide() {
        val navBar = findViewById<View>(R.id.nav_bar)
        val floatBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        navBar.visibility = View.GONE
        floatBtn.visibility = View.GONE
    }

    fun show() {
        val navBar = findViewById<View>(R.id.nav_bar)
        val floatBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        navBar.visibility = View.VISIBLE
        floatBtn.visibility = View.VISIBLE
    }
}