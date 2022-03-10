package com.example.appdefilmes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appdefilmes.R

class FichaTecnicaFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ficha_tecnica, container, false)
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