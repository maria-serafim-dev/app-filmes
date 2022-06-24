package com.example.appdefilmes.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.TabViewPagerAdapter
import com.example.appdefilmes.repository.UsuarioDAO
import com.example.appdefilmes.data.baseUrlImagem
import com.example.appdefilmes.databinding.FragmentInformacoesFilmeBinding
import com.example.appdefilmes.extensions.loadImage
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class InformacoesFilmeFragment : BottomSheetDialogFragment() {


    private lateinit var dialog : BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var binding : FragmentInformacoesFilmeBinding
    private val args: InformacoesFilmeFragmentArgs by navArgs()
    private val viewModels : FilmeViewModel by activityViewModels()
    private lateinit var filme : Filme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformacoesFilmeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        filme = args.filme
        inicializarActivity()
        inicializarTabsFragments()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    private fun inicializarActivity() {
        inicializarTextos()
        inicializarImagens()
        inicializarBotaoMinhaLista()
        inicializarBotaoVoltar()
    }

    private fun inicializarTabsFragments() {
        val adapter = TabViewPagerAdapter(this, filme)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = getString(adapter.tabsText[position])
        }.attach()
    }

    private fun inicializarTextos() {
        binding.tvNomeObra.text = filme.title
        binding.tvSinopse.text = filme.overview
    }

    private fun inicializarImagens() {
        with(binding) {
            imgCartaz.loadImage(baseUrlImagem + filme.poster_path)
            imgCartazFundo.loadImage(baseUrlImagem + filme.poster_path)
        }
    }

    private fun inicializarBotaoMinhaLista() {

        if (UsuarioDAO().usuarioLogado) {
            configurarTextoBotaoMinhaLista()
            ouvinteBotaoMinhaLista()
        } else {
            binding.btnMinhaLista.visibility = View.GONE
        }
    }

    private fun configurarTextoBotaoMinhaLista() {
        viewModels.filmesFavoritos.observe(viewLifecycleOwner){
            if(filme in it) modificarLayoutBotao(R.drawable.ic_adicionado, R.string.text_btn_minha_lista_adicionado)

        }
    }

    private fun abrirSnackBar(nomeFilme: String, mensagem: String) =
        Snackbar.make(binding.btnMinhaLista, getString(R.string.text_snack_bar, nomeFilme, mensagem), Snackbar.LENGTH_LONG)

    private fun abrirDialog() {
        activity?.let { it ->
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.titulo_dialog))
                .setIcon(R.drawable.ic_remover)
                .setMessage(resources.getString(R.string.mensagem_dialog, filme.title.toString()))
                .setNegativeButton(resources.getString(R.string.negativo_dialog)) { _, _ ->
                }
                .setPositiveButton(resources.getString(R.string.positivo_dialog)) { _ , _ ->
                    viewModels.removerFilmeFavorito(filme)
                    modificarLayoutBotao(R.drawable.ic_star, R.string.text_btn_minha_lista)
                    filme.title?.let { abrirSnackBar(it,"removido da").show() }
                }.show()
        }
    }

    private fun modificarLayoutBotao(idDrawable: Int, idTexto: Int) {
        binding.btnMinhaLista.setIconResource(idDrawable)
        binding.btnMinhaLista.text = getString(idTexto)
    }

    private fun inicializarBotaoVoltar() {
        binding.imgBack.setOnClickListener {
            dismiss()
        }
    }

    private fun ouvinteBotaoMinhaLista() {
        binding.btnMinhaLista.setOnClickListener {
            val textMinhaLista = getString(R.string.text_btn_minha_lista)
            if (binding.btnMinhaLista.text.equals(textMinhaLista)) {
                filme.let {
                    viewModels.adicionarFilmeFavorito(filme)
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
}