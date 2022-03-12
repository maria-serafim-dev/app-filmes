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
import com.example.appdefilmes.model.Filme


class InicioFragment(var listaFilmes: List<Filme>) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_inicio, container, false)

        adaptarRecycleView(view, R.id.recyclerViewNovidades)
        adaptarRecycleView(view, R.id.recyclerViewSucesso)
        adaptarRecycleView(view, R.id.recyclerViewExclusivos)

        return view

    }

    fun adaptarRecycleView(view: View, id: Int){
        val recyclerView: RecyclerView = view.findViewById(id)
        recyclerView.adapter = this.listaFilmes.let { FilmeAdapter(view.context, it) }
        val layoutManagerRecyclerView = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManagerRecyclerView
    }

}