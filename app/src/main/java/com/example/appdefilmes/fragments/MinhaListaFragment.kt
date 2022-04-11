package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.databinding.FragmentMinhaListaBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse


class MinhaListaFragment : Fragment() {

    private var _binding: FragmentMinhaListaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMinhaListaBinding.inflate(inflater, container, false)
        val view = binding.root
        filmesPopulares(view)
        return view
    }

    private fun filmesPopulares(view: View){
        FilmeDAO().getListaFavoritos(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                adaptarRecycleView(view, filmes)
            }

        })
    }

    private fun adaptarRecycleView(view: View, filmes: List<Filme>){
        val recyclerView: RecyclerView = binding.rvMinhaLista
        val adapter = FilmeAdapter(view.context, filmes)
        recyclerView.adapter = adapter
        adapter.setOnClick(object: InterfaceOnClick {
            override fun onItemClick(filme: Filme) {
                val action = MinhaListaFragmentDirections.actionMinhaListaFragment2ToInformacoesFilmeActivity(filme = filme)
                findNavController().navigate(action)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}