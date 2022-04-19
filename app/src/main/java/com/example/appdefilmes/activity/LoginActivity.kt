package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ouvinteBotaoLogin()
    }

    private fun ouvinteBotaoLogin() {
        binding.btnEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("signIn", "Sucesso ao logar usuário")
                    nextActivity()
                } else {
                    messageErro("e-mail e senha")
                    Log.i("signIn", "Erro ao logar usuário", task.exception)
                }
            }
        }
    }

    fun nextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun messageErro(provedor: String) {
        Toast.makeText(this, "Falha na autenticação com $provedor",Toast.LENGTH_LONG).show()
    }

}