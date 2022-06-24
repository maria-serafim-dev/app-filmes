package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdefilmes.repository.FilmeRepository
import com.example.appdefilmes.repository.UsuarioDAO
import com.example.appdefilmes.model.Filme
import kotlinx.coroutines.launch

class FilmeViewModel : ViewModel() {

    private val _filmesFavoritos = MutableLiveData<MutableList<Filme>>()
    private val repository : FilmeRepository by lazy{
        FilmeRepository()
    }

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


    init {
        if (UsuarioDAO().usuarioLogado) filmesFavoritos()
        getFilme()
    }

    private fun filmesFavoritos() {
        viewModelScope.launch {
            _filmesFavoritos.value = repository.getListaFavoritos()
        }
    }

    private fun getFilme() {
        viewModelScope.launch {
            try {
                _filmesNovidades.value = repository.getFilmesPorCategoria("top_rated")
                _filmesAtuaisNosCinemais.value = repository.getFilmesPorCategoria("now_playing")
                _filmesPopulares.value = repository.getFilmesPorCategoria("popular")
            } catch (e: Exception) {

            }
        }
    }

    fun getFilmesSimilares(id: Int) {
        viewModelScope.launch {
            _filmesSimilares.value = repository.getFilmesSimilares(id)
        }
    }

    fun adicionarFilmeFavorito(filme: Filme) {
        repository.inserirMinhaLista(filme)
        _filmesFavoritos.value?.add(filme)
        _filmesFavoritos.postValue(_filmesFavoritos.value)
    }

    fun removerFilmeFavorito(filme: Filme) {
        repository.removerFavorito(filme.id.toString())
        _filmesFavoritos.value?.remove(filme)
        _filmesFavoritos.postValue(_filmesFavoritos.value)
    }



}