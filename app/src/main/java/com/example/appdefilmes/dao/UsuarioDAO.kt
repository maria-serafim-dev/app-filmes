package com.example.appdefilmes.dao

import com.google.firebase.auth.FirebaseAuth

class UsuarioDAO {


    private var _usuarioLogado : Boolean = false
    val usuarioLogado: Boolean
        get() = _usuarioLogado

    private lateinit var _usuarioId : String
    val usuarioId: String
        get() = _usuarioId

    private lateinit var _usuarioNome : String
    val usuarioNome: String
        get() = _usuarioNome

    private lateinit var _usuarioEmail : String
    val usuarioEmail: String
        get() = _usuarioEmail

    private fun verificarLogin(){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            _usuarioLogado = true
            _usuarioId = auth.uid!!
            _usuarioNome = auth.currentUser?.displayName!!
            _usuarioEmail = auth.currentUser?.email!!
        }
    }

    init {
        verificarLogin()
    }

}