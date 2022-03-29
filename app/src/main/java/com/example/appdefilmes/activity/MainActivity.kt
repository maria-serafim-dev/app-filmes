package com.example.appdefilmes.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val fragment = InicioFragment()
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(binding.fragmentInicio.id, fragment)
                .commit()

        iniciarMenu()

    }


    private fun iniciarMenu() {
        binding.btNavegacaoInicio.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    val fragment = InicioFragment()
                    iniciarFragment(fragment)

                }
                R.id.page_2 -> {
                    val fragment = MinhaListaFragment()
                    iniciarFragment(fragment)
                }
            }
            false
        }
    }

    private fun iniciarFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentInicio.id, fragment)
        transaction.commit()
    }

}