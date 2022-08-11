package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.retrofit.UsuarioResponse
import com.example.appdefilmes.useCase.CadastroUsuarioUseCase
import com.example.appdefilmes.useCase.VerificarLoginUseCase

class UsuarioViewModel : ViewModel() {

    private val cadastroUseCase: CadastroUsuarioUseCase by lazy {
        CadastroUsuarioUseCase()
    }

    private val verificarLoginUseCase: VerificarLoginUseCase by lazy {
        VerificarLoginUseCase()
    }

    private var _logado = MutableLiveData(false)
    val logado: LiveData<Boolean> = _logado

    fun cadastrarUsuario(usuario: Usuario, response: UsuarioResponse) {
        cadastroUseCase.cadastrarUsuario(usuario, response)
    }

    private fun verificarLogin() {
        _logado.value = verificarLoginUseCase.verificarLogin()
    }

    init {
        verificarLogin()
    }

}