package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.adapters.recyclerview.adapter.InterfaceOnClick
import com.example.appdefilmes.databinding.FragmentInicioBinding
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.viewModel.FilmeViewModel


class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
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
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializarRecyclerView(view)
    }

    private fun inicializarRecyclerView(view: View) {
        viewModel.filmesPopulares.observe(viewLifecycleOwner) { listaFilme ->
            adaptarRecycleView(view, R.id.rv_sucesso, listaFilme)
        }

        viewModel.filmesAtuaisNosCinemais.observe(viewLifecycleOwner) { listaFilme ->
            adaptarRecycleView(view, R.id.rv_exclusivos, listaFilme)
        }

        viewModel.filmesNovidades.observe(viewLifecycleOwner) { listaFilme ->
            adaptarRecycleView(view, R.id.rv_novidades, listaFilme)
        }
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
                val action = InicioFragmentDirections.actionInicioFragment2ToInformacoesFilmeActivity(filme = filme)
                findNavController().navigate(action)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}