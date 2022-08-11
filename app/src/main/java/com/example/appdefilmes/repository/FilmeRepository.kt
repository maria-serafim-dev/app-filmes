package com.example.appdefilmes.repository

import com.example.appdefilmes.extensions.reduzirParaTamanhoDez
import com.example.appdefilmes.extensions.reduzirParaTamanhoNove
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await


class FilmeRepository {

    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun inserirMinhaLista(filme: Filme, usuarioId: String) {
        referencia.child("filmeFavoritos").child(usuarioId).child(filme.id.toString()).setValue(filme)
    }

    suspend fun getListaFavoritos(usuarioId: String): MutableList<Filme> {
        val listaFilmes: MutableList<Filme> = mutableListOf()
        val await = referencia.child("filmeFavoritos").child(usuarioId).get().await()
        await.children.forEach { dataSnapshot ->
            val filme: Filme? = dataSnapshot.getValue(Filme::class.java)
            filme?.let { it -> listaFilmes.add(it) }
        }
        return listaFilmes
    }

    suspend fun getFilmesPorCategoria(categoria: String): List<Filme>{
        return FilmeApi.retrofitService.getFilmes(categoria).results.reduzirParaTamanhoDez()
    }


    suspend fun getFilmesSimilares(id: Int): List<Filme>{
        return FilmeApi.retrofitService.getFilmeSimilaresId(id).results.reduzirParaTamanhoNove()
    }


    fun removerFavorito(id: String, usuarioId: String) {
        referencia.child("filmeFavoritos").child(usuarioId).child(id).removeValue()
    }

}