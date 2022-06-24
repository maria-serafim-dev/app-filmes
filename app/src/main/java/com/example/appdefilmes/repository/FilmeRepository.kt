package com.example.appdefilmes.repository

import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await


class FilmeRepository {

    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val usuarioId = UsuarioDAO().usuarioId


    fun inserirMinhaLista(filme: Filme) {
        referencia.child("filmeFavoritos").child(usuarioId).child(filme.id.toString()).setValue(filme)
    }

    suspend fun getListaFavoritos(): MutableList<Filme> {
        val listaFilmes: MutableList<Filme> = mutableListOf()
        val await = referencia.child("filmeFavoritos").child(usuarioId).get().await()
        await.children.forEach { dataSnapshot ->
            val filme: Filme? = dataSnapshot.getValue(Filme::class.java)
            filme?.let { it -> listaFilmes.add(it) }
        }
        return listaFilmes
    }

    suspend fun getFilmesPorCategoria(categoria: String): List<Filme>{
        return FilmeApi.retrofitService.getFilmes(categoria).results
    }


    suspend fun getFilmesSimilares(id: Int): List<Filme>{
        return FilmeApi.retrofitService.getFilmeSimilaresId(id).results
    }


    fun removerFavorito(id: String) {
        referencia.child("filmeFavoritos").child(usuarioId).child(id).removeValue()
    }

}