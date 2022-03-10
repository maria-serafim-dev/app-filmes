package com.example.appdefilmes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InformacoesFilmeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

        val imagem = findViewById<ImageView>(R.id.a_informacoes_back)

        imagem.setOnClickListener {
            voltarTela()
        }

        setupViews()
    }

    private fun setupViews() {
        val tabs = findViewById<TabLayout>(R.id.tabs)
        val viewPage2 = findViewById<ViewPager2>(R.id.viewpager2)
        val adapter = TabViewPagerAdapter(this)
        viewPage2.adapter = adapter

        TabLayoutMediator(tabs, viewPage2) { tab, position ->
           tab.text = getString(adapter.tabsText[position])
        }.attach()

    }

    private fun voltarTela() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}