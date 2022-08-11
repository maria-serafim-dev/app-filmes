package com.example.appdefilmes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.model.UsuarioLogin
import com.example.appdefilmes.repository.FilmeRepository
import com.example.appdefilmes.useCase.DadosUsuarioLogadoUseCase
import com.example.appdefilmes.useCase.VerificarLoginUseCase
import kotlinx.coroutines.launch

class FilmeViewModel : ViewModel() {


    private val repository : FilmeRepository by lazy{
        FilmeRepository()
    }

    private val verificarLoginUseCase: VerificarLoginUseCase by lazy {
        VerificarLoginUseCase()
    }

    private val usuarioLogadoUseCase: DadosUsuarioLogadoUseCase by lazy {
        DadosUsuarioLogadoUseCase()
    }

    private var _usuarioLogado = MutableLiveData<UsuarioLogin>()

    private val _filmesFavoritos = MutableLiveData<MutableList<Filme>>()
    val filmesFavoritos: LiveData<MutableList<Filme>>
        get() = _filmesFavoritos

    private val _filmesComMelhoresAvaliacoes = MutableLiveData<List<Filme>>()
    val filmesComMelhoresAvaliacoes: LiveData<List<Filme>>
        get() = _filmesComMelhoresAvaliacoes

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
        if (verificarLoginUseCase.verificarLogin()) {
            recuperarDadosUsuario()
            filmesFavoritos()
        }
        getFilme()
    }

    private fun filmesFavoritos() {
        viewModelScope.launch {
            _filmesFavoritos.value = _usuarioLogado.value?.let { repository.getListaFavoritos(it.id) }
        }
    }

    private fun getFilme() {
        viewModelScope.launch {
            try {
                _filmesComMelhoresAvaliacoes.value = repository.getFilmesPorCategoria("top_rated")
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
        _usuarioLogado.value?.let {repository.inserirMinhaLista(filme, it.id)}
        _filmesFavoritos.value?.add(filme)
        _filmesFavoritos.postValue(_filmesFavoritos.value)
    }

    fun removerFilmeFavorito(filme: Filme) {
        _usuarioLogado.value?.let {repository.removerFavorito(filme.id.toString(), it.id)}
        _filmesFavoritos.value?.remove(filme)
        _filmesFavoritos.postValue(_filmesFavoritos.value)
    }

    private fun recuperarDadosUsuario(){
        viewModelScope.launch{
            usuarioLogadoUseCase.recuperarDadosUsuario().collect{ usuario ->
                _usuarioLogado.value = usuario
            }
        }
    }

}