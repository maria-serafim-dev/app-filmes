package com.example.appdefilmes.repository

import android.net.Uri
import com.example.appdefilmes.data.erroCadastro
import com.example.appdefilmes.data.erroEmailExistente
import com.example.appdefilmes.data.sucessoCadastro
import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.model.token
import com.example.appdefilmes.retrofit.UsuarioResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UsuarioRepository {

    private val auth = FirebaseAuth.getInstance()
    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference

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

    private fun verificarLoginDadosDoUsuario(){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser != null) {
            _usuarioId = auth.uid!!
            _usuarioNome = auth.currentUser?.displayName!!
            _usuarioEmail = auth.currentUser?.email!!
            val provedor = auth.currentUser?.providerData?.get(1)?.providerId
            _usuarioFoto = if (provedor != null && provedor == "facebook.com") {
                Uri.parse("${auth.currentUser?.photoUrl}?access_token=${token}")
            }else{
                auth.currentUser?.photoUrl!!
            }
        }
    }

    init {
        verificarLoginDadosDoUsuario()
    }



    fun cadastrarUsuario(usuario: Usuario, response: UsuarioResponse) {
        auth.createUserWithEmailAndPassword(usuario.email, usuario.senha)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val idUsuario = auth.currentUser?.uid
                    if (idUsuario != null) {
                        armazenarTodosDadosUsuario(idUsuario, usuario)
                        atualizarNomeUsuario(auth.currentUser!!, usuario.nome, object : UsuarioResponse{
                            override fun resposta(resposta: Int) {
                                response.resposta(resposta)
                            }
                        })
                    }
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        response.resposta(erroEmailExistente)
                    }
                }
            }
    }


    private fun atualizarNomeUsuario(idUsuario: FirebaseUser, nome: String, resposta: UsuarioResponse) {

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(nome)
            .build()

       idUsuario.updateProfile(profileUpdates)
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    resposta.resposta(sucessoCadastro)
                }else{
                    resposta.resposta(erroCadastro)
                }
            }


    }

    private fun armazenarTodosDadosUsuario(idUsuario: String, usuario: Usuario) {
        referencia.child("dadosUsuario").child(idUsuario).setValue(usuario)
    }

    fun verificarLogin() = auth.currentUser != null
}