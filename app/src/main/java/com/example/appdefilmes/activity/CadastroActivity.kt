package com.example.appdefilmes.activity

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("Feminino", "Masculino", "Outro", "Não quero informar")
        val adapter = ArrayAdapter(applicationContext, R.layout.lista_item_input, items)
        (binding.tfGenero.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}