package com.example.appdefilmes.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.activity.InformacoesFilmeActivity
import com.example.appdefilmes.activity.MainActivity
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse


class InicioFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_inicio, container, false)

        filmesPopulares(view)
        filmesExclusivos(view)
        filmesNovidades(view)

        return view

    }

    fun adaptarRecycleView(view: View, id: Int, filmes: List<Filme>){
        val recyclerView: RecyclerView = view.findViewById(id)
        val adapter = filmes.let { FilmeAdapter(view.context, it) }
        recyclerView.adapter = adapter
        adapter.setOnClick(object: InterfaceOnClick{
            override fun onItemClick(filme: Filme) {
                telaInformacaoFilme(filme, view)
            }
        })
    }

    private fun telaInformacaoFilme(filme: Filme, view: View) {
        val intent = Intent(view.context, InformacoesFilmeActivity::class.java)
        intent.putExtra("filme", filme)
        startActivity(intent)
    }

    fun filmesPopulares(view: View){
        FilmeDAO().getFilmesPopulares(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, R.id.recyclerViewSucesso, filmes)
            }

        })
    }

    fun filmesNovidades(view: View){
        FilmeDAO().getFilmesBemAvaliados(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, R.id.recyclerViewNovidades, filmes)
            }

        })
    }

    fun filmesExclusivos(view: View){
        FilmeDAO().getFilmesPopulares(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, R.id.recyclerViewExclusivos, filmes)
            }

        })
    }



}