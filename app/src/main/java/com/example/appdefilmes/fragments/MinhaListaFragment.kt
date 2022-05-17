package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.databinding.FragmentMinhaListaBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel


class MinhaListaFragment : Fragment() {

    private var _binding: FragmentMinhaListaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMinhaListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filmesFavoritos.observe(viewLifecycleOwner) { listaFilme ->
            adaptarRecycleView(view, listaFilme)
        }
    }

    private fun adaptarRecycleView(view: View, filmes: List<Filme>) {
        val recyclerView: RecyclerView = binding.rvMinhaLista
        val adapter = FilmeAdapter(view.context, filmes, 2)
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