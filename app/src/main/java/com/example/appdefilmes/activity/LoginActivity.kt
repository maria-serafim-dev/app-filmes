package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.databinding.ActivityLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null
    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ouvinteBotaoLogin()
        ouvinteBotaoFacebook()
        registrarCallBackFacebook()
        clickListenerInputs()

    }

    private fun registrarCallBackFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    loginResult?.let { it1 -> sucessoFacebook(it1.accessToken) }
                }

                override fun onCancel() {
                    mensagemCancelar("Facebook")
                }

                override fun onError(exception: FacebookException) {
                    mensagemErro("Facebook")
                }
            })
    }

    private fun ouvinteBotaoFacebook() {
        binding.btFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                listOf("email", "public_profile", "user_friends")
            )
        }
    }

    private fun sucessoFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    nextActivity()
                } else {
                    Log.w("FaceBookLogin", "signInWithCredential:failure", task.exception)
                    mensagemErro("Facebook")
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun ouvinteBotaoLogin() {
        binding.btnEntrar.setOnClickListener {

            if(validarCampos()) {
                val email = binding.editEmail.text.toString()
                val senha = binding.editSenha.text.toString()

                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        nextActivity()
                    } else {
                        mensagemErro("e-mail e senha")
                        Log.i("signIn", "Erro ao logar usuário", task.exception)
                    }
                }
            }
        }
    }

    private fun nextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun mensagemErro(provedor: String) {
        Toast.makeText(this, "Falha na autenticação com $provedor", Toast.LENGTH_LONG).show()
    }
    private fun mensagemCancelar(provedor: String) {
        Toast.makeText(this, "Login com $provedor cancelado" , Toast.LENGTH_LONG).show()
    }


    private fun validarCampos(): Boolean {
        binding.tfEmail.error = null
        binding.tfSenha.error = null
        var retorno = true
        if (TextUtils.isEmpty(binding.editSenha.text) || binding.editSenha.text == null) {
            binding.tfSenha.error = "Dígite uma senha"
            binding.editSenha.requestFocus()
            retorno = false
        }
        if (TextUtils.isEmpty(binding.editEmail.text) || binding.editEmail.text == null) {
            binding.tfEmail.error = "Dígite um email"
            binding.editEmail.requestFocus()
            retorno = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.text).matches()) {
            binding.tfEmail.error = "E-mail inválido"
            binding.editEmail.requestFocus()
            retorno = false
        }
        return retorno
    }

    private fun clickListenerInputs() {
        binding.editEmail.setOnKeyListener { view, i, _ -> handleKeyEvent(view, i) }
        binding.editSenha.setOnKeyListener { view, i, _ -> handleKeyEvent(view, i) }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}