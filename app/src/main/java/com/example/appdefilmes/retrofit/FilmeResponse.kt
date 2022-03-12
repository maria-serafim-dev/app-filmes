package com.example.appdefilmes.retrofit

import com.example.appdefilmes.model.Filme

interface FilmeResponse {

    fun sucesso(filmes: List<Filme>)

}