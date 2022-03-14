package com.example.appdefilmes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
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
        recyclerView.adapter = filmes.let { FilmeAdapter(view.context, it) }
        val layoutManagerRecyclerView = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManagerRecyclerView
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