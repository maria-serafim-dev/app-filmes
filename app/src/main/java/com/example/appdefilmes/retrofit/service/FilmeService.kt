package com.example.appdefilmes.retrofit.service

import com.example.appdefilmes.data.idioma
import com.example.appdefilmes.data.qtdePagina
import com.example.appdefilmes.data.regiao
import com.example.appdefilmes.data.chaveAPI
import com.example.appdefilmes.model.Result
import retrofit2.http.GET
import retrofit2.http.Path


interface FilmeService {

    @GET("/3/movie/{category}?api_key=${chaveAPI}&language=${idioma}&page=${qtdePagina}&region=${regiao}")
    suspend fun getFilmes(
        @Path("category") categoria: String
    ) : Result

    @GET("/3/movie/{movie_id}/similar?api_key=${chaveAPI}&language=${idioma}&page=${qtdePagina}")
    suspend fun getFilmeSimilaresId(
        @Path("movie_id") idFilme: Int
    ) : Result

}