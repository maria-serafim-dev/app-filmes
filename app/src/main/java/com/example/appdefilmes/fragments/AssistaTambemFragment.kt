package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.data.layoutAssistaTambem
import com.example.appdefilmes.databinding.FragmentAssistaTambemBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel


class AssistaTambemFragment(var filme: Filme?) : Fragment() {

    private lateinit var binding: FragmentAssistaTambemBinding
    private val viewModel: FilmeViewModel by viewModels()
    private val adapter: FilmeAdapter by lazy {
        FilmeAdapter(
            layoutAssistaTambem
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssistaTambemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarObservable()
        setOnClickFilme()
        setAdapter()
    }

    private fun iniciarObservable(){
        filme?.id?.let {
            viewModel.getFilmesSimilares(it)
            viewModel.filmesSimilares.observe(viewLifecycleOwner) { listaFilme ->
                adapter.submitList(listaFilme)
            }
        }
    }

    private fun setAdapter() {
        binding.rvAssitaTambem.adapter = adapter
    }

    private fun setOnClickFilme() {
        adapter.setOnClick(object : InterfaceOnClick {
            override fun onItemClick(filme: Filme) {
                abrirTelaInformacaoFilme(filme)
            }
        })
    }


    private fun abrirTelaInformacaoFilme(filme: Filme) {
        val action = InformacoesFilmeFragmentDirections.actionInformacoesFilmeFragmentSelf2(filme)
        findNavController().navigate(action)
    }


    override fun onResume() {
        super.onResume()
        setProperHeightOfView()
    }

    private fun setProperHeightOfView() {
        val layoutParams = binding.assista.layoutParams
        if (layoutParams != null) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.assista.requestLayout()
        }
    }
}