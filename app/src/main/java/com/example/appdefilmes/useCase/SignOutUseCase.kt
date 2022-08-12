package com.example.appdefilmes.useCase

import com.example.appdefilmes.repository.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class SignOutUseCase {

    private val repository: LoginRepository by lazy {
        LoginRepository()
    }

    operator fun invoke(mGoogleSignInClient: GoogleSignInClient) {
        return repository.singOutTodosProvedores(mGoogleSignInClient)
    }
}