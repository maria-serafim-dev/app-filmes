package com.example.appdefilmes.dao

import android.util.Log
import com.example.appdefilmes.model.ApiKey
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.model.Result
import com.example.appdefilmes.retrofit.FilmeResponse
import com.example.appdefilmes.retrofit.FilmeRetrofit
import com.example.appdefilmes.retrofit.service.FilmeService
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmeDAO {

    val BASE_URL: String = "https://api.themoviedb.org"
    val API_KEY: String = ApiKey().apiKey
    var category: String = ""
    val LANGUAGE: String = "pt-BR"
    val ID_FILME: Int = 634649
    val PAGE: Int = 1
    val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
    val endpoint = retrofit.create(FilmeService::class.java)
    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().getReference()

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

    fun getListaFavoritos(){
        val minhaLista: DatabaseReference = referencia.child("minhaLista").child("001")


        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue().toString()
                Log.i("ListaFavoritos", post)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ListaFavoritos", "loadPost:onCancelled", error.toException())
            }

        }
        minhaLista.addValueEventListener(postListener)

    }

}