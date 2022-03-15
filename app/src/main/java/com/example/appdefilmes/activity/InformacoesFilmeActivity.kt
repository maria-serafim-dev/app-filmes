package com.example.appdefilmes.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.model.Filme
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class InformacoesFilmeActivity : AppCompatActivity() {

    var umFilme: Filme? = null
    var urlDaImagem = "https://image.tmdb.org/t/p/w500"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

        inicializarActivity()
        inicializarTabsFragments()
    }

    private fun inicializarActivity() {
        umFilme = intent.extras?.getParcelable("filme")

        val titulo = findViewById<TextView>(R.id.text_nome_obra)
        titulo.text = umFilme?.title

        val descricao = findViewById<TextView>(R.id.text_sinopse)
        descricao.text = umFilme?.overview

        val imagemPoster = findViewById<ImageView>(R.id.a_informacoes_cartaz)
        val imagemPosterBackground = findViewById<ImageView>(R.id.a_informacoes_cartaz_fundo)
        urlDaImagem += umFilme?.poster_path
        receberImagens(urlDaImagem, imagemPoster, imagemPosterBackground)

        val buttonMinhaLista = findViewById<MaterialButton>(R.id.button_minha_lista)
        val dao = FilmeDAO()
        dao.verificaFilmeFavorito(umFilme?.id.toString()) {
            if (it) {
                buttonMinhaLista.setIconResource(R.drawable.ic_adicionado)
                buttonMinhaLista.text = getString(R.string.button_minha_lista_adicionado)
            }
        }

        val imagem = findViewById<ImageView>(R.id.a_informacoes_back)

        imagem.setOnClickListener {
            finish()
        }



        buttonMinhaLista.setOnClickListener {

            if (buttonMinhaLista.text.equals("Minha Lista")) {
                umFilme?.let {
                    dao.inserirMinhaLista(it)
                    buttonMinhaLista.setIconResource(R.drawable.ic_adicionado)
                    buttonMinhaLista.text = getString(R.string.button_minha_lista_adicionado)
                }
            } else {
                Log.i("INFO", "false")
            }
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

}