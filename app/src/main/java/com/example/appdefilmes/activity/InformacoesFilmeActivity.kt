package com.example.appdefilmes.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.dao.UsuarioDAO
import com.example.appdefilmes.databinding.ActivityInformacoesFilmeBinding
import com.example.appdefilmes.model.Filme
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class InformacoesFilmeActivity : AppCompatActivity() {

    private var umFilme: Filme? = null
    private var urlDaImagem = "https://image.tmdb.org/t/p/w500"
    private val dao = FilmeDAO()

    private lateinit var binding: ActivityInformacoesFilmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacoesFilmeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        inicializarActivity()
        inicializarTabsFragments()

    }

    private fun inicializarActivity() {
        umFilme = intent.extras?.getParcelable("filme")

        inicializarTextos()
        inicializarImagens()
        inicializarBotaoMinhaLista()
        inicializarBotaoVoltar()
    }

    private fun inicializarTabsFragments() {
        val adapter = TabViewPagerAdapter(this, umFilme)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = getString(adapter.tabsText[position])
        }.attach()
    }

    private fun inicializarTextos() {
        binding.tvNomeObra.text = umFilme?.title
        binding.tvSinopse.text = umFilme?.overview
    }

    private fun inicializarImagens() {
        urlDaImagem += umFilme?.poster_path
        Picasso.get().load(urlDaImagem).into(binding.imgCartaz)
        Picasso.get().load(urlDaImagem).into(binding.imgCartazFundo)
    }

    private fun inicializarBotaoMinhaLista() {

        if(UsuarioDAO().usuarioLogado) {
            configurarTextoBotaoMinhaLista()
            ouvinteBotaoMinhaLista()

        }else{
            binding.btnMinhaLista.visibility = View.GONE
        }
    }

    private fun ouvinteBotaoMinhaLista() {
        binding.btnMinhaLista.setOnClickListener {
            val textMinhaLista = getString(R.string.text_btn_minha_lista)
            if (binding.btnMinhaLista.text.equals(textMinhaLista)) {
                umFilme?.let {
                    dao.inserirMinhaLista(it)
                    modificarLayoutBotao(
                        R.drawable.ic_adicionado,
                        R.string.text_btn_minha_lista_adicionado
                    )
                    abrirSnackBar(it.title!!, "adicionado à").show()
                }
            } else {
                abrirDialog()
            }
        }
    }

    private fun configurarTextoBotaoMinhaLista() {
        dao.verificaFilmeFavorito(umFilme?.id.toString()) {
            if (it) {
                modificarLayoutBotao(
                    R.drawable.ic_adicionado,
                    R.string.text_btn_minha_lista_adicionado
                )
            }
        }
    }

    private fun abrirSnackBar(nomeFilme: String, mensagem: String) =
        Snackbar.make(binding.btnMinhaLista, getString(R.string.text_snack_bar, nomeFilme, mensagem), Snackbar.LENGTH_LONG)

    private fun abrirDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.titulo_dialog))
            .setIcon(R.drawable.ic_remover)
            .setMessage(resources.getString(R.string.mensagem_dialog, umFilme?.title.toString()))
            .setNegativeButton(resources.getString(R.string.negativo_dialog)) { _, _ ->
            }
            .setPositiveButton(resources.getString(R.string.positivo_dialog)) { _ , _ ->
                dao.removerFavorito(umFilme?.id.toString())
                modificarLayoutBotao(R.drawable.ic_star, R.string.text_btn_minha_lista)
                umFilme?.title?.let { abrirSnackBar(it,"removido da").show() }
            }.show()
    }

    private fun inicializarBotaoVoltar() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun modificarLayoutBotao(idDrawable: Int, idTexto: Int) {
        binding.btnMinhaLista.setIconResource(idDrawable)
        binding.btnMinhaLista.text = getString(idTexto)
    }





}