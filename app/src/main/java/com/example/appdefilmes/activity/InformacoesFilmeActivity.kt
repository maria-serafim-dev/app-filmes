package com.example.appdefilmes.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.example.appdefilmes.dao.FilmeDAO
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

        dao.verificaFilmeFavorito(umFilme?.id.toString()) {
            if (it) {
                modificarLayoutBotao(
                    R.drawable.ic_adicionado,
                    R.string.button_minha_lista_adicionado
                )
            }
        }

        binding.btMinhaLista.setOnClickListener {
            val textMinhaLista = getString(R.string.button_minha_lista)
            if (binding.btMinhaLista.text.equals(textMinhaLista)) {
                umFilme?.let {
                    dao.inserirMinhaLista(it)
                    modificarLayoutBotao(
                        R.drawable.ic_adicionado,
                        R.string.button_minha_lista_adicionado
                    )
                    abrirSnackBar("Filme inserido com sucesso").show()
                }
            }else{
                abrirDialog()
            }
        }
    }

    private fun abrirSnackBar(mensagem: String) =
        Snackbar.make(binding.btMinhaLista, mensagem, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.white))
            .setActionTextColor(ContextCompat.getColor(this, R.color.gray))

    private fun abrirDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.titulo_dialog))
            .setIcon(R.drawable.ic_remover)
            .setMessage(resources.getString(R.string.mensagem_dialog, umFilme?.title.toString()))
            .setNegativeButton(resources.getString(R.string.negativo_dialog)) { _, _ ->
            }
            .setPositiveButton(resources.getString(R.string.positivo_dialog)) { _ , _ ->
                dao.removerFavorito(umFilme?.id.toString())
                modificarLayoutBotao(R.drawable.ic_star, R.string.button_minha_lista)
                abrirSnackBar("Filme removido com sucesso").show()
            }.show()
    }

    private fun inicializarBotaoVoltar() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun modificarLayoutBotao(idDrawable: Int, idTexto: Int) {
        binding.btMinhaLista.setIconResource(idDrawable)
        binding.btMinhaLista.text = getString(idTexto)
    }





}