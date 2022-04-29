package com.example.appdefilmes.dao

import com.google.firebase.auth.FirebaseAuth

class UsuarioDAO {


    private var _usuarioLogado : Boolean = false
    val usuarioLogado: Boolean
        get() = _usuarioLogado

    private fun verificarLogin(){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) _usuarioLogado = true
    }

    init {
        verificarLogin()
    }

}