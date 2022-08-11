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
import com.example.appdefilmes.data.layoutInicio
import com.example.appdefilmes.databinding.FragmentInicioBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel


class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding
    private val viewModel: FilmeViewModel by activityViewModels()

    private val adapterComMelhoresAvaliacoes: FilmeAdapter by lazy {
        FilmeAdapter(
            layoutInicio
        )
    }

    private val adapterPopulares: FilmeAdapter by lazy {
        FilmeAdapter(
            layoutInicio
        )
    }

    private val adapterAtuaisNosCinemas: FilmeAdapter by lazy {
        FilmeAdapter(
            layoutInicio
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarObservable()
        setAdapter()
        setOnClickFilme(adapterPopulares)
        setOnClickFilme(adapterComMelhoresAvaliacoes)
        setOnClickFilme(adapterAtuaisNosCinemas)
    }

    private fun iniciarObservable() {
        viewModel.filmesPopulares.observe(viewLifecycleOwner) { listaFilme ->
            adapterPopulares.submitList(listaFilme)
        }

        viewModel.filmesAtuaisNosCinemais.observe(viewLifecycleOwner) { listaFilme ->
            adapterAtuaisNosCinemas.submitList(listaFilme)
        }

        viewModel.filmesComMelhoresAvaliacoes.observe(viewLifecycleOwner) { listaFilme ->
            adapterComMelhoresAvaliacoes.submitList(listaFilme)
        }
    }

    private fun setAdapter() {
        binding.rvPopulares.adapter = adapterPopulares
        binding.rvComMelhoresAvaliacoes.adapter = adapterComMelhoresAvaliacoes
        binding.rvAtuaisNosCinemas.adapter = adapterAtuaisNosCinemas
    }

    private fun setOnClickFilme(adapter: FilmeAdapter) {
        adapter.setOnClick(object : InterfaceOnClick {
            override fun onItemClick(filme: Filme) {
                abrirTelaInformacaoFilme(filme)
            }
        })
    }

    private fun abrirTelaInformacaoFilme(filme: Filme) {
        val action = InicioFragmentDirections.actionInicioFragment2ToInformacoesFilmeFragment(filme = filme)
        findNavController().navigate(action)
    }
}