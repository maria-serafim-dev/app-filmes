package com.example.appdefilmes.viewModel

import androidx.lifecycle.ViewModel
import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.retrofit.UsuarioResponse
import com.example.appdefilmes.useCase.CadastroUsuarioUseCase

class UsuarioViewModel : ViewModel() {

    private val cadastroUseCase: CadastroUsuarioUseCase by lazy {
        CadastroUsuarioUseCase()
    }


    fun cadastrarUsuario(usuario: Usuario, response: UsuarioResponse) {
        cadastroUseCase.cadastrarUsuario(usuario, response)
    }

}