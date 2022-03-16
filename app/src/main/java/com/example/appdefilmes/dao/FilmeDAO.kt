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

    private val baseUrl: String = "https://api.themoviedb.org"
    private val chaveAPI: String = ApiKey().apiKey
    private var category: String = ""
    private val idioma: String = "pt-BR"
    private val regiao: String = "BR"
    private val qtdePagina: Int = 1
    private val retrofit = FilmeRetrofit.getRetrofitInstance(baseUrl)
    private val endpoint = retrofit.create(FilmeService::class.java)
    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun getFilmesPopulares(filmeResponse: FilmeResponse) {
        category = "popular"
        listaFilmes(filmeResponse, category)
    }

    fun getFilmesBemAvaliados(filmeResponse: FilmeResponse) {
        category = "top_rated"
        listaFilmes(filmeResponse, category)
    }


    private fun listaFilmes(filmeResponse: FilmeResponse, categoria: String){
        val callback = endpoint.getFilmes(categoria, chaveAPI, idioma, qtdePagina, regiao)
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


    fun getFilmesSimilares(filmeResponse: FilmeResponse, id: Int) {
        category = "latest"
        val callback = endpoint.getFilmeSimilaresId(id, chaveAPI, idioma, qtdePagina)

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

    fun getFilmeAtuaisNosCinemais(filmeResponse: FilmeResponse) {
        category = "now_playing"
        val callback = endpoint.getFilmes(category, chaveAPI, idioma, qtdePagina, regiao)

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
        val query: Query = referencia.child("minhaLista")
        val listaFilmes: MutableList<Filme> = mutableListOf()

        query.addValueEventListener(object : ValueEventListener {
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

    fun verificaFilmeFavorito(id: String, callback: (Boolean) -> Unit){
        referencia.child("minhaLista").child(id).get().addOnSuccessListener {
            if(it.value != null) callback(true)
            else callback(false)

        }.addOnFailureListener{
            Log.e("DAO:FilmeFavorito", "Erro ao obter dados", it)
        }

    }

    fun removerFavorito(id: String){
       referencia.child("minhaLista").child(id).removeValue()
    }

}