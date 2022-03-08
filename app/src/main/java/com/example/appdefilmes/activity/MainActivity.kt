package com.example.appdefilmes.activity

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.appdefilmes.R
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = InicioFragment()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment).commit()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(selecionarMenu)

    }

    private val selecionarMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
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
                startActivity(intent)
            }
            else -> false
        }
        true
    }

    private fun iniciarFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}