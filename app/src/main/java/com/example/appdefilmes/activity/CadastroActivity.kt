package com.example.appdefilmes.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("Feminino", "Masculino", "Outro", "Não quero informar")
        val estadosBrasileiros = listOf("Acre (AC)", "Alagoas (AL)","Amapá (AP)","Amazonas (AM)","Bahia (BA)","Ceará (CE)", "Distrito Federal (DF)","Espírito Santo (ES)", "Goiás (GO)",
            "Maranhão (MA)","Mato Grosso (MT)","Mato Grosso do Sul (MS)","Minas Gerais (MG)","Pará (PA)","Paraíba (PB)","Paraná (PR)","Pernambuco (PE)",
            "Piauí (PI)","Rio de Janeiro (RJ)","Rio Grande do Norte (RN)","Rio Grande do Sul (RS)","Rondônia (RO)","Roraima (RR)","Santa Catarina (SC)","São Paulo (SP)",
            "Sergipe (SE)","Tocantins (TO)")

        val adapterGenero = ArrayAdapter (applicationContext, R.layout.lista_item_input, items)
        (binding.tfGenero.editText as? AutoCompleteTextView)?.setAdapter(adapterGenero)

        val adapterEstados = ArrayAdapter (applicationContext, R.layout.lista_item_input, estadosBrasileiros)
        (binding.tfEstado.editText as? AutoCompleteTextView)?.setAdapter(adapterEstados)
    }
}