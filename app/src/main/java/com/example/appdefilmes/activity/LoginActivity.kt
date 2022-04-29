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
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null
    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ouvinteBotaoLogin()
        ouvinteBotaoFacebook()
        registrarCallBackFacebook()
        clickListenerInputs()
        inicializarLoginGoogle()
        clickListenerBotaoGoogle()

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
        binding.btnFacebook.setOnClickListener {
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
                    proximaActivity()
                } else {
                    Log.w("FaceBookLogin", "signInWithCredential:failure", task.exception)
                    mensagemErro("Facebook")
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun ouvinteBotaoLogin() {
        binding.btnEntrar.setOnClickListener {

            if(validarCampos()) {
                val email = binding.editEmail.text.toString()
                val senha = binding.editSenha.text.toString()

                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        proximaActivity()
                    } else {
                        mensagemErro("e-mail e senha")
                        Log.i("signIn", "Erro ao logar usuário", task.exception)
                    }
                }
            }
        }
    }

    private fun proximaActivity() {
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
            binding.tfSenha.error = getString(R.string.erro_input_senha)
            binding.editSenha.requestFocus()
            retorno = false
        }
        if (TextUtils.isEmpty(binding.editEmail.text) || binding.editEmail.text == null) {
            binding.tfEmail.error = getString(R.string.erro_input_email)
            binding.editEmail.requestFocus()
            retorno = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.text.toString()).matches()) {
            binding.tfEmail.error = getString(R.string.erro_input_email_invalido)
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

    private fun inicializarLoginGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun clickListenerBotaoGoogle() {
        binding.btnGoogle.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthComGoogle(account.idToken)
        } catch (e: ApiException) {
            Log.w("Error message", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun firebaseAuthComGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    proximaActivity()
                } else {
                    mensagemErro("Google")
                    Log.w(
                        "GoogleLogin",
                        "signInWithCredential:failure",
                        task.exception
                    )
                }
            }
    }
}