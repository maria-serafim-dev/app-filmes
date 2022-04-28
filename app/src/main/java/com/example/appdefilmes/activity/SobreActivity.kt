package com.example.appdefilmes.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.data.assuntoEmail
import com.example.appdefilmes.data.corpoEmail
import com.example.appdefilmes.data.enderecoEmail
import com.example.appdefilmes.databinding.ActivitySobreBinding

class SobreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySobreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarActionBar()
        ouvinteBotaoComentario()
    }

    private fun ouvinteBotaoComentario() {
        binding.btnEnviarComentario.setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, enderecoEmail)
                putExtra(Intent.EXTRA_SUBJECT, assuntoEmail)
                putExtra(Intent.EXTRA_TEXT, corpoEmail)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

    }

    private fun configurarActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}