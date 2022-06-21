package com.example.appdefilmes.dao

import com.example.appdefilmes.model.Filme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await


class FilmeDAO {

    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val usuarioId = UsuarioDAO().usuarioId


    fun inserirMinhaLista(filme: Filme) {
        referencia.child("filmeFavoritos").child(usuarioId).child(filme.id.toString()).setValue(filme)
    }

    suspend fun getListaFavoritos(): MutableList<Filme> {
        val listaFilmes: MutableList<Filme> = mutableListOf()
        val await = referencia.child("filmeFavoritos").child(usuarioId).get().await()
        await.children.forEach {
            val filme: Filme? = it.getValue(Filme::class.java)
            filme?.let { it1 -> listaFilmes.add(it1) }
        }
        return listaFilmes
    }

    fun removerFavorito(id: String) {
        referencia.child("filmeFavoritos").child(usuarioId).child(id).removeValue()
    }

}