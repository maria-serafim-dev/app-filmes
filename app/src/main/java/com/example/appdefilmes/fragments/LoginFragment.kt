package com.example.appdefilmes.fragments

import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.FragmentLoginBinding
import com.example.appdefilmes.viewModel.UsuarioViewModel
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : BottomSheetDialogFragment() {
    private lateinit var dialog : BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private lateinit var callbackManager: CallbackManager
    private lateinit var binding: FragmentLoginBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    private val viewModelUsuario: UsuarioViewModel by activityViewModels()

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        navController = findNavController()
        ouvinteBotaoLogin()
        ouvinteBotaoFacebook()
        registrarCallBackFacebook()
        clickListenerInputs()
        inicializarLoginGoogle()
        clickListenerBotaoGoogle()
        ouvinteBotaoCadastrar()


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    private fun ouvinteBotaoCadastrar() {
        binding.tvNaoPossuiConta.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCadastroFragment()
            navController.navigate(action)
        }
    }

    private fun registrarCallBackFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    sucessoFacebook(result.accessToken)
                }

                override fun onCancel() {
                    mensagemCancelar("Facebook")
                }

                override fun onError(error: FacebookException) {
                    mensagemErro("Facebook")
                }
            })
    }

    private fun ouvinteBotaoFacebook() {
        binding.btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                callbackManager,
                listOf("email", "public_profile", "user_friends")
            )
        }
    }

    private fun sucessoFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    retornarUsuarioLogado()
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
        }
    }

    private fun ouvinteBotaoLogin() {
        binding.btnEntrar.setOnClickListener {

            if(validarCampos()) {
                val email = binding.editEmail.text.toString()
                val senha = binding.editSenha.text.toString()

                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        retornarUsuarioLogado()
                    } else {
                        mensagemErro("e-mail e senha")
                        Log.i("signIn", "Erro ao logar usuário", task.exception)
                    }
                }
            }
        }
    }

    private fun retornarUsuarioLogado() {
        viewModelUsuario.logado.value = true
        navController.popBackStack()
    }

    private fun mensagemErro(provedor: String) {
        Toast.makeText(context, "Falha na autenticação com $provedor", Toast.LENGTH_LONG).show()
    }
    private fun mensagemCancelar(provedor: String) {
        Toast.makeText(context, "Login com $provedor cancelado" , Toast.LENGTH_LONG).show()
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
            val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
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
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext() , gso)
    }

    private fun clickListenerBotaoGoogle() {
        binding.btnGoogle.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        super.startActivityForResult(signInIntent, RC_SIGN_IN)
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
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    retornarUsuarioLogado()
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