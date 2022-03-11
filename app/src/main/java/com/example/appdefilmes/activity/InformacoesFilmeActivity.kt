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
import com.example.appdefilmes.model.Filme
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class InformacoesFilmeActivity : AppCompatActivity() {

    var umFilme: Filme? = null
    var URL_IMAGEM = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

        inicializarActivity()
        inicializarTabsFragments()
    }

    private fun inicializarActivity() {
        umFilme = intent.extras?.getParcelable<Filme>("filme")

        val titulo = findViewById<TextView>(R.id.text_nome_obra)
        titulo.text = umFilme?.title

        val descricao = findViewById<TextView>(R.id.text_sinopse)
        descricao.text = umFilme?.overview

        val imagemPoster = findViewById<ImageView>(R.id.a_informacoes_cartaz)
        val imagemPosterBackground = findViewById<ImageView>(R.id.a_informacoes_cartaz_fundo)
        URL_IMAGEM += umFilme?.poster_path
        receberImagens(URL_IMAGEM, imagemPoster, imagemPosterBackground)

        val imagem = findViewById<ImageView>(R.id.a_informacoes_back)

        imagem.setOnClickListener {
            voltarTela()
        }

    }

    private fun receberImagens(url: String, imagem: ImageView?, imagemBack: ImageView?) {
        Picasso.get().load(url).into(imagem)
        Picasso.get().load(url).into(imagemBack)
    }

    private fun inicializarTabsFragments() {
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