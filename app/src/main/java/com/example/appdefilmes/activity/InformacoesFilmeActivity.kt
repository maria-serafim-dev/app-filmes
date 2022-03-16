package com.example.appdefilmes.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.model.Filme
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class InformacoesFilmeActivity : AppCompatActivity() {

    var umFilme: Filme? = null
    var urlDaImagem = "https://image.tmdb.org/t/p/w500"
    val dao = FilmeDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_filme)

        inicializarActivity()
        inicializarTabsFragments()
    }

    private fun inicializarActivity() {
        umFilme = intent.extras?.getParcelable("filme")

        inicializarTextos()
        inicializarImagens()
        inicializarBotoes()
    }

    private fun inicializarBotoes() {
        val buttonMinhaLista = findViewById<MaterialButton>(R.id.button_minha_lista)
        dao.verificaFilmeFavorito(umFilme?.id.toString()) {
            if (it) {
                modificarLayoutBotao(buttonMinhaLista, R.drawable.ic_adicionado, R.string.button_minha_lista_adicionado)
            }
        }

        buttonMinhaLista.setOnClickListener {
            val textMinhaLista = getString(R.string.button_minha_lista)
            if (buttonMinhaLista.text.equals(textMinhaLista)) {
                umFilme?.let {
                    dao.inserirMinhaLista(it)
                    modificarLayoutBotao(buttonMinhaLista, R.drawable.ic_adicionado, R.string.button_minha_lista_adicionado)
                }
            }else{
                abrirDialog(buttonMinhaLista)
            }
        }
    }

    private fun abrirDialog(buttonMinhaLista: MaterialButton) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.titulo_dialog))
            .setMessage(resources.getString(R.string.mensagem_dialog))
            .setNeutralButton(resources.getString(R.string.cancelar_dialog)) { dialog, which ->
            }
            .setNegativeButton(resources.getString(R.string.negativo_dialog)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.positivo_dialog)) { dialog, which ->
                dao.removerFavorito(umFilme?.id.toString())
                modificarLayoutBotao(buttonMinhaLista, R.drawable.ic_star, R.string.button_minha_lista)
            }
            .show()
    }

    private fun modificarLayoutBotao(buttonMinhaLista: MaterialButton, idDrawable: Int, idTexto: Int) {
        buttonMinhaLista.setIconResource(idDrawable)
        buttonMinhaLista.text = getString(idTexto)
    }

    private fun inicializarImagens() {
        val imagemPoster = findViewById<ImageView>(R.id.a_informacoes_cartaz)
        val imagemPosterBackground = findViewById<ImageView>(R.id.a_informacoes_cartaz_fundo)
        urlDaImagem += umFilme?.poster_path
        receberImagens(urlDaImagem, imagemPoster, imagemPosterBackground)

        val iconBack = findViewById<ImageView>(R.id.a_informacoes_back)
        iconBack.setOnClickListener {
            finish()
        }

    }

    private fun inicializarTextos() {
        val titulo = findViewById<TextView>(R.id.text_nome_obra)
        titulo.text = umFilme?.title

        val descricao = findViewById<TextView>(R.id.text_sinopse)
        descricao.text = umFilme?.overview
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