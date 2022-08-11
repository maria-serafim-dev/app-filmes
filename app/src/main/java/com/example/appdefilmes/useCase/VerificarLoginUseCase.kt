package com.example.appdefilmes.useCase

import com.example.appdefilmes.repository.UsuarioRepository

class VerificarLoginUseCase {

    private val repository: UsuarioRepository by lazy {
        UsuarioRepository()
    }

    operator fun invoke() : Boolean {
        return repository.verificarLogin()
    }
}