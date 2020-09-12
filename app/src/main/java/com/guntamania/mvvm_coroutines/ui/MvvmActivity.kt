package com.guntamania.mvvm_coroutines.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.guntamania.mvvm_coroutines.R
import com.guntamania.mvvm_coroutines.ui.main.MainViewModel
import org.koin.android.ext.android.inject

class MvvmActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navController = findNavController(R.id.nav_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.navigation)
        setupWithNavController(bottomNav, navController)
    }

}