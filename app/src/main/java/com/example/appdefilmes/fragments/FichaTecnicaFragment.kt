package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appdefilmes.databinding.FragmentFichaTecnicaBinding
import com.example.appdefilmes.model.Filme

class FichaTecnicaFragment(var filme: Filme?) : Fragment() {

    private var _binding: FragmentFichaTecnicaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFichaTecnicaBinding.inflate(inflater, container, false)
        val view = binding.root

        atribuirValoresFilme()

        return view
    }

    private fun atribuirValoresFilme() {
        binding.tvNomeFilme.text = filme?.title
        binding.tvValorAno.text = filme?.release_date
        binding.tvValorNomeOriginal.text = filme?.original_title
    }

    override fun onResume() {
        super.onResume()
        setProperHeightOfView()
    }

    private fun setProperHeightOfView() {
        val layoutParams = binding.ficha.layoutParams
        if (layoutParams != null) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.ficha.requestLayout()
        }
    }
}