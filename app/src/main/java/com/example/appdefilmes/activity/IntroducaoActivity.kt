package com.example.appdefilmes.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityIntroducaoBinding


class IntroducaoActivity : AppCompatActivity(){

    private lateinit var binding : ActivityIntroducaoBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityIntroducaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_introducao) as NavHostFragment
        navController = navHostFragment.navController

    }

}