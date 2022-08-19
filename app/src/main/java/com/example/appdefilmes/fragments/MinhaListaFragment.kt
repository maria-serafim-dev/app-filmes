package com.example.appdefilmes.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.data.layoutMinhaLista
import com.example.appdefilmes.databinding.FragmentMinhaListaBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel
import com.example.appdefilmes.viewModel.UsuarioViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MinhaListaFragment : Fragment() {

    private lateinit var binding: FragmentMinhaListaBinding
    private val viewModelFilmes: FilmeViewModel by activityViewModels()
    private val viewModelUsuario: UsuarioViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var dialog : AlertDialog? = null

    private val adapter: FilmeAdapter by lazy {
        FilmeAdapter(
            layoutMinhaLista
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMinhaListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        setAdapter()
        setOnClickFilme()
        verificarUsuarioLogado()
    }

    private fun abrirDialog() {
        val materialAlertDialogBuilder = context?.let { MaterialAlertDialogBuilder(it) }
        materialAlertDialogBuilder?.
        setTitle("Usuário não Logado")?.
        setMessage("Para acessar essa sessão você precisa estar logado . Deseja logar ou criar uma conta?")?.
        setCancelable(false)?.
        setPositiveButton("Sim") { _: DialogInterface, _: Int -> }?.
        setNegativeButton("Continuar sem logar") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            voltarParaHome()
        }

        dialog = materialAlertDialogBuilder?.show()!!

        dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }
    }

    private fun voltarParaHome() {
        val startDestination = navController.graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()
        navController.navigate(startDestination, null, navOptions)
    }

    private fun verificarUsuarioLogado() {
        viewModelUsuario.logado.observe(viewLifecycleOwner) { usuarioLogado ->
            if (!usuarioLogado) {
                abrirDialog()
            }else{
                dialog?.dismiss()
                iniciarObservable()
            }
        }
    }

    private fun iniciarObservable() {
        viewModelUsuario.usuarioLogado.observe(viewLifecycleOwner) { usuario ->
            viewModelFilmes.getFilmesFavoritos(usuario)
            viewModelFilmes.filmesFavoritos.observe(viewLifecycleOwner) { listaFilme ->
                val listaFilmesImutavel: List<Filme> = listaFilme
                adapter.submitList(listaFilmesImutavel.toList())
                if (listaFilme.isEmpty()) binding.tvCrieSuaLista.visibility = View.VISIBLE
            }
        }
    }

    private fun setAdapter() {
        binding.rvMinhaLista.adapter = adapter
    }

    private fun setOnClickFilme() {
        adapter.setOnClick(object : InterfaceOnClick {
            override fun onItemClick(filme: Filme) {
                abrirTelaInformacaoFilme(filme)
            }
        })
    }

    private fun abrirTelaInformacaoFilme(filme: Filme) {
        val action = MinhaListaFragmentDirections.actionMinhaListaFragment2ToInformacoesFilmeFragment(filme = filme)
        findNavController().navigate(action)
    }
}