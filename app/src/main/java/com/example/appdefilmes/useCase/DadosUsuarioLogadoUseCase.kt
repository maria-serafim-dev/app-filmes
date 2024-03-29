package com.example.appdefilmes.useCase

import com.example.appdefilmes.model.UsuarioLogin
import com.example.appdefilmes.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow

class DadosUsuarioLogadoUseCase {

    private val repository: UsuarioRepository by lazy {
        UsuarioRepository()
    }

    suspend operator fun invoke(): Flow<UsuarioLogin> {
       return repository.recuperarDadosUsuarioLogado()
    }
}