package com.example.appdefilmes.dao

import android.util.Log
import com.example.appdefilmes.model.ApiKey
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.model.Result
import com.example.appdefilmes.retrofit.FilmeResponse
import com.example.appdefilmes.retrofit.FilmeRetrofit
import com.example.appdefilmes.retrofit.service.FilmeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmeDAO {

    val BASE_URL: String = "https://api.themoviedb.org"
    val API_KEY: String = ApiKey().apiKey
    val CATEGORY: String = "popular"
    val LANGUAGE: String = "pt-BR"
    val ID_FILME: Int = 634649
    val PAGE: Int = 1
    val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
    val endpoint = retrofit.create(FilmeService::class.java)

    fun getFilme(filmeResponse: FilmeResponse) {
        val callback = endpoint.getFilmeId(ID_FILME, API_KEY, LANGUAGE)

        callback.enqueue(object : Callback<Filme> {
            override fun onResponse(call: Call<Filme>, response: Response<Filme>) {

                val results: Filme? = response.body()

            }

            override fun onFailure(call: Call<Filme>, t: Throwable) {
                Log.i("Retrofit", t.message.toString())
            }
        })

    }

    fun getFilmesPopulares(filmeResponse: FilmeResponse) {

        val callback = endpoint.getFilmes(CATEGORY, API_KEY, LANGUAGE, PAGE)

        callback.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                response?.body()?.let{
                    val results: Result = it
                    val listaFilmes: List<Filme> = results.results
                    filmeResponse.sucesso(listaFilmes)
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.i("Retrofit", t.message.toString())
            }

        })



    }

}