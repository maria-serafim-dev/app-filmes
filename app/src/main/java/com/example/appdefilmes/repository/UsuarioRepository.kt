package com.example.appdefilmes.repository

import android.net.Uri
import android.util.Log
import com.example.appdefilmes.data.erroCadastro
import com.example.appdefilmes.data.erroEmailExistente
import com.example.appdefilmes.data.sucessoCadastro
import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.model.UsuarioLogin
import com.example.appdefilmes.retrofit.UsuarioResponse
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsuarioRepository {

    private val auth = FirebaseAuth.getInstance()
    private var referencia: DatabaseReference = FirebaseDatabase.getInstance().reference

    suspend fun recuperarDadosUsuarioLogado() : Flow<UsuarioLogin> = flow {
        val accessToken = AccessToken.getCurrentAccessToken()
        if(auth.currentUser != null) {
            val usuarioId = auth.uid!!
            val usuarioNome = auth.currentUser?.displayName!!
            val usuarioEmail = auth.currentUser?.email!!

            val provedor = auth.currentUser?.providerData?.get(1)?.providerId
            Log.i("Usuario", provedor.toString())
            val usuarioFoto = if (provedor != null && provedor == "facebook.com") {
                Uri.parse("${auth.currentUser?.photoUrl}?access_token=${accessToken?.token}")
            }else if (provedor != null && provedor == "google.com"){
                auth.currentUser?.photoUrl!!
            }else{
                Uri.parse("null")
            }

            val usuarioLogin = UsuarioLogin(usuarioId, usuarioNome, usuarioEmail, usuarioFoto)

            emit(usuarioLogin)
        }else{
            throw Exception("Não foi possível fazer a busca no momento")
        }
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