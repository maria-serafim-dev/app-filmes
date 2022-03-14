package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.model.Filme


class AssistaTambemFragment(val listaFilmes: List<Filme>?) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_assista_tambem, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAssitaTambem)
        recyclerView.adapter = this.listaFilmes.let { FilmeAdapter(view.context, it) }
        val layoutManagerRecyclerView = GridLayoutManager(view.context, 3)
        recyclerView.layoutManager = layoutManagerRecyclerView

        return view

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