package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse
import com.google.firebase.auth.FirebaseAuth

class FilmeViewModel : ViewModel() {

    private val _filmesFavoritos = MutableLiveData<List<Filme>>()
    val filmesFavoritos: LiveData<List<Filme>>
        get() = _filmesFavoritos

    private fun filmesPopulares(){
        val auth = FirebaseAuth.getInstance().currentUser
        FilmeDAO().getListaFavoritos(auth?.uid, object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                _filmesFavoritos.value = filmes
            }
        })
    }

    init {
        filmesPopulares()
    }

}