package com.example.appdefilmes.dao

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.appdefilmes.model.ApiKey
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.model.Result
import com.example.appdefilmes.retrofit.FilmeResponse
import com.example.appdefilmes.retrofit.FilmeRetrofit
import com.example.appdefilmes.retrofit.service.FilmeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FilmeDAO {

    val BASE_URL: String = "https://api.themoviedb.org"
    val API_KEY: String = ApiKey().apiKey
    var category: String = ""
    val LANGUAGE: String = "pt-BR"
    val ID_FILME: Int = 634649
    val PAGE: Int = 1
    val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
    val endpoint = retrofit.create(FilmeService::class.java)


    fun getFilmesPopulares(filmeResponse: FilmeResponse) {
        category = "popular"
        val callback = endpoint.getFilmes(category, API_KEY, LANGUAGE, PAGE)

        callback.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                response.body()?.let{
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

    fun getFilmesNovidades(filmeResponse: FilmeResponse) {
        category = "latest"
        val callback = endpoint.getFilmes(category, API_KEY, LANGUAGE, PAGE)

        callback.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                response.body()?.let{
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

    fun getFilmesBemAvaliados(filmeResponse: FilmeResponse) {
        category = "top_rated"
        val callback = endpoint.getFilmes(category, API_KEY, LANGUAGE, PAGE)

        callback.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                response.body()?.let{
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