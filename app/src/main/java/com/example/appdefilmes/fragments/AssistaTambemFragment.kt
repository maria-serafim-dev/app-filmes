package com.example.appdefilmes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.activity.InformacoesFilmeActivity
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse


class AssistaTambemFragment(var filme: Filme?) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_assista_tambem, container, false)
        filmesPopulares(view)
        return view
    }

    fun filmesPopulares(view: View){
        filme?.id?.let {
            FilmeDAO().getFilmesSimilares(object: FilmeResponse {
                override fun sucesso(filmes: List<Filme>) {
                    adaptarRecycleView(view, R.id.recyclerViewAssitaTambem, filmes)
                }

            }, it)
        }
    }

    fun adaptarRecycleView(view: View, id: Int, filmes: List<Filme>){
        val recyclerView: RecyclerView = view.findViewById(id)
        val adapter = filmes.let { FilmeAdapter(view.context, it) }
        recyclerView.adapter = adapter
        adapter.setOnClick(object: InterfaceOnClick {
            override fun onItemClick(filme: Filme) {
                telaInformacaoFilme(filme, view)

            }
        })
    }

    private fun telaInformacaoFilme(filme: Filme, view: View) {
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
        val layoutView = view?.findViewById<View>(R.id.assista)
        if (layoutView != null) {
            val layoutParams = layoutView.layoutParams
            if (layoutParams != null) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutView.requestLayout()
            }
        }
    }
}