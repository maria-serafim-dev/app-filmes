package com.example.appdefilmes.repository

import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class LoginRepository {

    fun singOutTodosProvedores(mGoogleSignInClient: GoogleSignInClient) {
        signOutEmailSenha()
        signOutFacebook()
        signOutGoogle(mGoogleSignInClient)
    }

    private fun signOutGoogle(mGoogleSignInClient: GoogleSignInClient) {
        mGoogleSignInClient.signOut()
    }

    private fun signOutEmailSenha() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun signOutFacebook() {
        LoginManager.getInstance().logOut()
    }


}