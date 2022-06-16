package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.data.layoutMinhaLista
import com.example.appdefilmes.databinding.FragmentMinhaListaBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel


class MinhaListaFragment : Fragment() {

    private lateinit var binding: FragmentMinhaListaBinding
    private val viewModel: FilmeViewModel by activityViewModels()
    private val adapter: FilmeAdapter by lazy {
        FilmeAdapter(
            layoutMinhaLista
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        iniciarObservable()
        setAdapter()
        setOnClickFilme()
    }

    private fun iniciarObservable() {
        viewModel.filmesFavoritos.observe(viewLifecycleOwner) { listaFilme ->
            val listaNova : List<Filme> = listaFilme
            adapter.submitList(listaNova.toList())
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