package com.example.appdefilmes.dao

import android.util.Log
import com.example.appdefilmes.data.*
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.model.Result
import com.example.appdefilmes.retrofit.FilmeResponse
import com.example.appdefilmes.retrofit.FilmeRetrofit
import com.example.appdefilmes.retrofit.service.FilmeService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilmeDAO {

    private var category: String = ""
    private val retrofit = FilmeRetrofit.getRetrofitInstance(baseUrl)
    private val endpoint = retrofit.create(FilmeService::class.java)
    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance().currentUser
    private val userId = auth?.uid


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
        if (userId != null) {
            referencia.child("filmeFavoritos").child(userId).child(filme.id.toString()).setValue(filme)
        }
    }

    fun getListaFavoritos(filmeResponse: FilmeResponse){
        lateinit var query: Query
        if (userId != null) {
            query = referencia.child("filmeFavoritos").child(userId)
        }
        val listaFilmes: MutableList<Filme> = mutableListOf()

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listaFilmes.clear()
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

    fun verificaFilmeFavorito(idFilme: String, callback: (Boolean) -> Unit){
        if (userId != null) {
            referencia.child("filmeFavoritos").child(userId).child(idFilme).get().addOnSuccessListener {
                if (it.value != null) callback(true)
                else callback(false)

            }.addOnFailureListener {
                Log.e("DAO:FilmeFavorito", "Erro ao obter dados", it)
            }
        }

    }

    fun removerFavorito(id: String){
        if (userId != null) {
            referencia.child(userId).child(id).removeValue()
        }
    }

}