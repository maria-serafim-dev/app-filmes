package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.appdefilmes.databinding.ActivityPrincipalBinding
import com.google.firebase.auth.FirebaseAuth

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ouvinteBotaoCadastrarMaisTarde()
        ouvinteBotaoLogin()
        ouvinteBotaoCadastrar()
    }

    private fun ouvinteBotaoCadastrarMaisTarde() {
        binding.btnCriarMaisTarde.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ouvinteBotaoLogin() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ouvinteBotaoCadastrar() {
        binding.btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}