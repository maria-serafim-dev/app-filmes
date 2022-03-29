package com.example.appdefilmes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appdefilmes.R
import com.example.appdefilmes.activity.InformacoesFilmeActivity
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.databinding.FragmentInicioBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse


class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val view = binding.root

        buscarFilmesPopulares(view)
        buscarFilmesAtuaisNosCinemais(view)
        buscarFilmesNovidades(view)

        return view

    }

    private fun buscarFilmesPopulares(view: View){
        FilmeDAO().getFilmesPopulares(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, R.id.rv_sucesso, filmes)
            }

        })
    }

    private fun buscarFilmesNovidades(view: View){
        FilmeDAO().getFilmesBemAvaliados(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, R.id.rv_novidades, filmes)
            }

        })
    }

    private fun buscarFilmesAtuaisNosCinemais(view: View){
        FilmeDAO().getFilmeAtuaisNosCinemais(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, R.id.rv_exclusivos, filmes)
            }

        })
    }

    private fun adaptarRecycleView(view: View, id: Int, filmes: List<Filme>){
        val adapter = FilmeAdapter(view.context, filmes)
        when(id){
            R.id.rv_sucesso -> binding.rvSucesso.adapter = adapter
            R.id.rv_novidades -> binding.rvNovidades.adapter = adapter
            R.id.rv_exclusivos -> binding.rvExclusivos.adapter = adapter
        }

        adapter.setOnClick(object: InterfaceOnClick{
            override fun onItemClick(filme: Filme) {
                abrirTelaInformacaoFilme(filme, view)
            }
        })
    }

    private fun abrirTelaInformacaoFilme(filme: Filme, view: View) {
        val intent = Intent(view.context, InformacoesFilmeActivity::class.java)
        intent.putExtra("filme", filme)
        startActivity(intent)
    }
}