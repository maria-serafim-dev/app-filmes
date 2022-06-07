package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.dao.UsuarioDAO
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeApi
import kotlinx.coroutines.launch

class FilmeViewModel : ViewModel() {

    private val _filmesFavoritos = MutableLiveData<MutableList<Filme>>()
    val filmesFavoritos: LiveData<MutableList<Filme>>
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

    private val _filmesSimilares = MutableLiveData<List<Filme>>()
    val filmesSimilares: LiveData<List<Filme>>
        get() = _filmesSimilares


    private fun filmesFavoritos() {
        viewModelScope.launch {
            _filmesFavoritos.value = FilmeDAO().getListaFavoritos()
        }
    }

    private fun getFilme() {
        viewModelScope.launch {
            try {
                _filmesPopulares.value = FilmeApi.retrofitService.getFilmes("popular").results
                _filmesNovidades.value = FilmeApi.retrofitService.getFilmes("top_rated").results
                _filmesAtuaisNosCinemais.value = FilmeApi.retrofitService.getFilmes("now_playing").results
            } catch (e: Exception) {

            }
        }
    }

    fun getFilmesSimilares(id: Int) {
        viewModelScope.launch {
            _filmesSimilares.value = FilmeApi.retrofitService.getFilmeSimilaresId(id).results
        }
    }

    fun adicionarFilmeFavorito(filme: Filme) {
        FilmeDAO().inserirMinhaLista(filme)
        _filmesFavoritos.value?.add(filme)
        _filmesFavoritos.postValue(_filmesFavoritos.value)
    }

    fun removerFilmeFavorito(filme: Filme) {
        FilmeDAO().removerFavorito(filme.id.toString())
        _filmesFavoritos.value?.remove(filme)
        _filmesFavoritos.postValue(_filmesFavoritos.value)
    }

    init {
        if (UsuarioDAO().usuarioLogado) filmesFavoritos()
        getFilme()
    }

}