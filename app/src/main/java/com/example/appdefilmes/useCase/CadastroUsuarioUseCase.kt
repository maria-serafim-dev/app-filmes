package com.example.appdefilmes.useCase

import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.repository.UsuarioRepository
import com.example.appdefilmes.retrofit.UsuarioResponse

class CadastroUsuarioUseCase {

    private val  repository: UsuarioRepository by lazy {
        UsuarioRepository()
    }

    operator fun invoke(usuario: Usuario, response: UsuarioResponse){
        repository.cadastrarUsuario(usuario, response)
    }


}