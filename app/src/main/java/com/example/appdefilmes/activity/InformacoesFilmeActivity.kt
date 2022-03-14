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
    var listaFilmes: List<Filme>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

       umFilme = Filme(634649, "en", "Spider-Man: No Way Home", "Peter Parker é desmascarado e não consegue mais separar sua vida normal dos grandes riscos de ser um super-herói. Quando ele pede ajuda ao Doutor Estranho, os riscos se tornam ainda mais perigosos, e o forçam a descobrir o que realmente significa ser o Homem-Aranha.","/fVzXp3NwovUlLe7fvoRynCmBPNc.jpg", "2021-12-15", "Homem-Aranha: Sem Volta Para Casa", 8.3, 9110)
        val filme2 = Filme(null, null, null, null,"/cKNxg77ll8caX3LulREep4C24Vx.jpg", null, null, null, null)
        val filme3 = Filme(null, null, null, null,"/dvTTuRqwvM6wkDuA0JviE58NSRp.jpg", null, null, null, null)
        val filme4 = Filme(null, null, null, null,"/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg", null, null, null, null)
        val filme5 = Filme(null, null, null, null,"/bktIW44oeurTptHLdkj7ayc4zbH.jpg", null, null, null, null)
        val filme6 = Filme(null, null, null, null,"/mC66fsuzlYHSoZwb6y6emSCaRv5.jpg", null, null, null, null)
        val filme7 = Filme(null, null, null, null,"/pVL9AyKKLfUwrYD6jhdsI15gBQ7.jpg", null, null, null, null)
        val filme8 = Filme(null, null, null, null,"/DUNRzBjRKE1pvl2jiiooDLafKf.jpg", null, null, null, null)
        val filme9 = Filme(null, null, null, null,"/pe17f8VDfzbvbHSAKAlcORtBHmW.jpg", null, null, null, null)
        listaFilmes = mutableListOf<Filme>(umFilme!!, filme2, filme3, filme4, filme5, filme6, filme7,filme8, filme9)


        inicializarActivity()
        inicializarTabsFragments()
    }

    private fun inicializarActivity() {
        //umFilme = intent.extras?.getParcelable<Filme>("filme")

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