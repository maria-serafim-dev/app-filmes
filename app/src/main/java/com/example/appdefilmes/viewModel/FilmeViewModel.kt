package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse

class FilmeViewModel : ViewModel() {

    private val _filmesFavoritos = MutableLiveData<List<Filme>>()
    val filmesFavoritos: LiveData<List<Filme>>
        get() = _filmesFavoritos


    private val _filmesNovidades = MutableLiveData<List<Filme>>()
    val filmesNovidades: LiveData<List<Filme>>
        get() = _filmesNovidades


    private val _filmesPopulares = MutableLiveData<List<Filme>>()
    val filmesPopulares: LiveData<List<Filme>>
        get() = _filmesPopulares

    private val _filmesAtuaisNosCinemais = MutableLiveData<List<Filme>>()
    val filmesAtuaisNosCinemais: LiveData<List<Filme>>
        get() = _filmesAtuaisNosCinemais


    private fun filmesPopulares(){
        FilmeDAO().getListaFavoritos(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                _filmesFavoritos.value = filmes
            }
        })
    }


    private fun buscarFilmesPopulares() {
        FilmeDAO().getFilmesPopulares(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                _filmesPopulares.value = filmes
            }
        })
    }

    private fun buscarFilmesNovidades() {
        FilmeDAO().getFilmesBemAvaliados(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                _filmesNovidades.value = filmes
            }
        })
    }

    private fun buscarFilmesAtuaisNosCinemais(){
        FilmeDAO().getFilmeAtuaisNosCinemais(object: FilmeResponse {
            override fun sucesso(filmes: List<Filme>) {
                _filmesAtuaisNosCinemais.value = filmes
            }
        })
    }

    init {
        filmesPopulares()
        buscarFilmesPopulares()
        buscarFilmesNovidades()
        buscarFilmesAtuaisNosCinemais()
    }

}