package com.example.appdefilmes.repository

import android.net.Uri
import com.example.appdefilmes.model.token
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

    private lateinit var _usuarioFoto : Uri
    val usuarioFoto: Uri
        get() = _usuarioFoto

    private fun verificarLogin(){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            _usuarioLogado = true
            _usuarioId = auth.uid!!
            _usuarioNome = auth.currentUser?.displayName!!
            _usuarioEmail = auth.currentUser?.email!!
            val provedor = auth.currentUser?.providerData?.get(1)?.providerId
            if (provedor != null && provedor.equals("facebook.com")) {
                _usuarioFoto = Uri.parse("${auth.currentUser?.photoUrl}?access_token=${token}")
            }else{
                _usuarioFoto = auth.currentUser?.photoUrl!!
            }

        }
    }

    init {
        verificarLogin()
    }

}