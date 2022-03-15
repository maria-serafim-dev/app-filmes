package com.example.appdefilmes.dao

import android.util.Log
import com.example.appdefilmes.model.*
import com.example.appdefilmes.retrofit.FilmeResponse
import com.example.appdefilmes.retrofit.FilmeRetrofit
import com.example.appdefilmes.retrofit.service.FilmeService
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilmeDAO {

    private val BASE_URL: String = "https://api.themoviedb.org"
    private val API_KEY: String = ApiKey().apiKey
    private var category: String = ""
    private val LANGUAGE: String = "pt-BR"
    private val PAGE: Int = 1
    private val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
    private val endpoint = retrofit.create(FilmeService::class.java)
    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference

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


    fun inserirMinhaLista(filme: Filme){
            referencia.child("minhaLista").child(filme.id.toString()).setValue(filme)
    }

    fun getListaFavoritos(filmeResponse: FilmeResponse){
        val minhaLista: DatabaseReference = referencia.child("minhaLista")
        val listaFilmes: MutableList<Filme> = mutableListOf()

        minhaLista.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val filme: Filme? = it.getValue(Filme::class.java)
                    filme?.let { it1 -> listaFilmes.add(it1) }
                }
                filmeResponse.sucesso(listaFilmes)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DAO:ListaFavoritos", "Falha ao ler o valor.", error.toException())
            }
        })


    }

}