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
import com.example.appdefilmes.useCase.VerificarLoginUseCase
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

    private var _usuarioLogado = MutableLiveData<UsuarioLogin>()
    val usuarioLogado: LiveData<UsuarioLogin> = _usuarioLogado

    private var _logado = MutableLiveData(false)
    val logado: LiveData<Boolean> = _logado

    fun cadastrarUsuario(usuario: Usuario, response: UsuarioResponse) {
        cadastroUseCase.cadastrarUsuario(usuario, response)
    }

    private fun verificarLogin() {
        _logado.value = verificarLoginUseCase.verificarLogin()
        if(_logado.value == true) recuperarDadosUsuario()
    }

    private fun recuperarDadosUsuario(){
        viewModelScope.launch{
            usuarioLogadoUseCase.recuperarDadosUsuario().collect{ usuario ->
                _usuarioLogado.value = usuario
            }
        }
    }

    init {
        verificarLogin()
    }

}