package com.example.appdefilmes.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_inicio) as NavHostFragment
        navController = navHostFragment.navController
        binding.btNavegacaoInicio.setupWithNavController(navController)

        abrirToast(view)

       binding.drawerLayout.openDrawer(Gravity.LEFT)

    }

    private fun abrirToast(view: View) {

        val nome = auth.currentUser?.displayName
        Toast.makeText(view.context, "Bem vinda $nome", Toast.LENGTH_LONG).show()
    }


}