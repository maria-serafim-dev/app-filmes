package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.appdefilmes.R
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment
import com.example.appdefilmes.model.Filme
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var umFilme: Filme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = InicioFragment()
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment)
                .commit()

        iniciarMenu()

    }


    private fun iniciarMenu() {
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    val fragment = InicioFragment()
                    iniciarFragment(fragment)

                }
                R.id.page_2 -> {
                    val fragment = MinhaListaFragment()
                    iniciarFragment(fragment)
                }
                R.id.page_3 -> {
                    val intent = Intent(this, InformacoesFilmeActivity::class.java)
                    intent.putExtra("filme", umFilme)
                    startActivity(intent)
                }
            }
            false
        }
    }

    private fun iniciarFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
    }

}