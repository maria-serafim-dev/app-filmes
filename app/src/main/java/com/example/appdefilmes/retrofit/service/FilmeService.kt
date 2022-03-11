package com.example.appdefilmes.retrofit.service

import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.model.UmFilme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FilmeService {

    @GET("/3/movie/{category}")
    fun getFilmes(
        @Path("category") categoria: String,
        @Query("api_key") chaveApi: String,
        @Query("language") idioma: String,
    ) : Call<Filme>

    @GET("/3/movie/{movie_id}")
    fun getFilmeId(
        @Path("movie_id") idFilme: Int,
        @Query("api_key") chaveApi: String,
        @Query("language") idioma: String,
    ) : Call<UmFilme>
}