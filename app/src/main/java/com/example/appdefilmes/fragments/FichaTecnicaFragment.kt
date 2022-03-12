package com.example.appdefilmes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.appdefilmes.R
import com.example.appdefilmes.model.Filme

class FichaTecnicaFragment(var filme: Filme?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ficha_tecnica, container, false)
        val titulo = view.findViewById<TextView>(R.id.text_nome_filme)
        titulo.text = filme?.title

        val ano = view.findViewById<TextView>(R.id.txt_valor_ano)
        ano.text = filme?.release_date

        return view
    }

    override fun onResume() {
        super.onResume()
        setProperHeightOfView()
    }

    private fun setProperHeightOfView() {
        val layoutView = view?.findViewById<View>(R.id.ficha)
        if (layoutView != null) {
            val layoutParams = layoutView.layoutParams
            if (layoutParams != null) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutView.requestLayout()
            }
        }
    }
}