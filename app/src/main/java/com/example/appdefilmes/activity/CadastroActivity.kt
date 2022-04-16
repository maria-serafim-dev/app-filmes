package com.example.appdefilmes.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.R
import com.example.appdefilmes.data.itensEstadosBrasileiros
import com.example.appdefilmes.data.itensGenero
import com.example.appdefilmes.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciarInputsDropdown()

    }

    private fun iniciarInputsDropdown() {
        val adapterGenero = ArrayAdapter(applicationContext, R.layout.lista_item_input, itensGenero)
        (binding.tfGenero.editText as? AutoCompleteTextView)?.setAdapter(adapterGenero)

        val adapterEstados =
            ArrayAdapter(applicationContext, R.layout.lista_item_input, itensEstadosBrasileiros)
        (binding.tfEstado.editText as? AutoCompleteTextView)?.setAdapter(adapterEstados)
    }
}