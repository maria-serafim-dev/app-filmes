package com.example.appdefilmes.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.appdefilmes.activity.InformacoesFilmeActivity
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.databinding.FragmentAssistaTambemBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel


class AssistaTambemFragment(var filme: Filme?) : Fragment() {

    private var _binding: FragmentAssistaTambemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssistaTambemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscarFilmesPopulares(view)
    }

    private fun buscarFilmesPopulares(view: View){
        filme?.id?.let {
            viewModel.getFilmesSimilares(it)
            viewModel.filmesSimilares.observe(viewLifecycleOwner) { listaFilme ->
                adaptarRecycleView(view, listaFilme)
            }
        }
    }

    private fun adaptarRecycleView(view: View, filmes: List<Filme>){
        val adapter = FilmeAdapter(view.context, filmes, 3)
        binding.rvAssitaTambem.adapter = adapter
        adapter.setOnClick(object: InterfaceOnClick {
            override fun onItemClick(filme: Filme) {
                abrirTelaInformacaoFilme(filme, view)
            }
        })
    }

    private fun abrirTelaInformacaoFilme(filme: Filme, view: View) {
        val intent = Intent(view.context, InformacoesFilmeActivity::class.java)
        intent.putExtra("filme", filme)
        startActivity(intent)
        activity?.finish()
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