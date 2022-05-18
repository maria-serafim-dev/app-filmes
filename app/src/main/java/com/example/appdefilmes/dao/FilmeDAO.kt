package com.example.appdefilmes.dao

import android.util.Log
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse
import com.google.firebase.database.*


class FilmeDAO {

     private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val usuarioId = UsuarioDAO().usuarioId


    fun inserirMinhaLista(filme: Filme){
        referencia.child("filmeFavoritos").child(usuarioId).child(filme.id.toString()).setValue(filme)
    }

    fun getListaFavoritos(filmeResponse: FilmeResponse){
        lateinit var query: Query
        query = referencia.child("filmeFavoritos").child(usuarioId)
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
        referencia.child("filmeFavoritos").child(usuarioId).child(idFilme).get().addOnSuccessListener {
            if (it.value != null) callback(true)
            else callback(false)

        }.addOnFailureListener {
            Log.e("DAO:FilmeFavorito", "Erro ao obter dados", it)
        }
    }

    fun removerFavorito(id: String){
        referencia.child("filmeFavoritos").child(usuarioId).child(id).removeValue()
    }

}