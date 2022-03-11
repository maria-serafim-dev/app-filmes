package com.example.appdefilmes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.example.appdefilmes.model.UmFilme
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlin.math.log


class InformacoesFilmeActivity : AppCompatActivity() {

    var umFilme: UmFilme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

        umFilme = intent.extras?.getParcelable<UmFilme>("filme")


        val titulo = findViewById<TextView>(R.id.text_nome_obra)
        titulo.text = umFilme?.title

        val descricao = findViewById<TextView>(R.id.text_sinopse)
        descricao.text = umFilme?.overview

        val imagemPoster = findViewById<ImageView>(R.id.a_informacoes_cartaz)
        val url = "https://image.tmdb.org/t/p/w500" + umFilme?.poster_path
        Picasso.get().load(url).into(imagemPoster)

        val imagemPosterBackground = findViewById<ImageView>(R.id.a_informacoes_cartaz_fundo)
        val urlBack = "https://image.tmdb.org/t/p/w500" + umFilme?.backdrop_path
        Picasso.get().load(url).into(imagemPosterBackground)


        val imagem = findViewById<ImageView>(R.id.a_informacoes_back)

        imagem.setOnClickListener {
            voltarTela()
        }

        setupViews()
    }

    private fun setupViews() {
        //val homenAranha = Result(false, "qualquer_coisa")

        val tabs = findViewById<TabLayout>(R.id.tabs)
        val viewPage2 = findViewById<ViewPager2>(R.id.viewpager2)
        val adapter = TabViewPagerAdapter(this, umFilme)
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