package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.model.UsuarioLogin
import com.example.appdefilmes.retrofit.UsuarioResponse
import com.example.appdefilmes.useCase.CadastroUsuarioUseCase
import com.example.appdefilmes.useCase.DadosUsuarioLogadoUseCase
import com.example.appdefilmes.useCase.SignOutUseCase
import com.example.appdefilmes.useCase.VerificarLoginUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val cadastroUseCase: CadastroUsuarioUseCase by lazy {
        CadastroUsuarioUseCase()
    }

    private val verificarLoginUseCase: VerificarLoginUseCase by lazy {
        VerificarLoginUseCase()
    }

    private val usuarioLogadoUseCase: DadosUsuarioLogadoUseCase by lazy {
        DadosUsuarioLogadoUseCase()
    }

    private val signOut: SignOutUseCase by lazy {
        SignOutUseCase()
    }

    private var _usuarioLogado = MutableLiveData<UsuarioLogin>()
    val usuarioLogado: LiveData<UsuarioLogin> = _usuarioLogado

    var logado = MutableLiveData(false)

    fun cadastrarUsuario(usuario: Usuario, response: UsuarioResponse) {
        cadastroUseCase(usuario, response)
    }

    private fun verificarLogin() {
        logado.value = verificarLoginUseCase()
        if(logado.value == true) recuperarDadosUsuario()
    }

    fun recuperarDadosUsuario() {
        viewModelScope.launch {
            usuarioLogadoUseCase().collect { usuario ->
                _usuarioLogado.value = usuario
            }
        }
    }

    fun signOutTodosProvedores(mGoogleSignInClient: GoogleSignInClient) {
        signOut(mGoogleSignInClient)
    }

    init {
        verificarLogin()
    }

}