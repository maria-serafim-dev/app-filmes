package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appdefilmes.databinding.FragmentFichaTecnicaBinding
import com.example.appdefilmes.model.Filme

class FichaTecnicaFragment(var filme: Filme?) : Fragment() {

    private lateinit var binding: FragmentFichaTecnicaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFichaTecnicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        atribuirValoresFilme()
    }
    private fun atribuirValoresFilme() {
        binding.tvNomeFilme.text = filme?.title
        binding.tvValorAno.text = filme?.formatarDataDeAcordoComALocalidade()
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