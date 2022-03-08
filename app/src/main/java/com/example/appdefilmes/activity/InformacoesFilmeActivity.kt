package com.example.appdefilmes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appdefilmes.R
import com.example.appdefilmes.fragments.AssistaTambemFragment
import com.example.appdefilmes.fragments.FichaTecnicaFragment
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class InformacoesFilmeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

        val fragment = AssistaTambemFragment()
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView2, fragment).commit()
        }

        selecionarTabs()
        val imagem = findViewById<ImageView>(R.id.a_informacoes_back)
        imagem.setOnClickListener {
            voltarTela()
        }

    }

    private fun voltarTela() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun selecionarTabs(){
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val valor_tab = tab?.position

                when (valor_tab) {
                    0 -> {
                        val fragment = AssistaTambemFragment()
                        iniciarFragment(fragment)
                    }
                    1 -> {
                        val fragment = FichaTecnicaFragment()
                        iniciarFragment(fragment)
                        finish()
                    }
                    else -> false
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun iniciarFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}