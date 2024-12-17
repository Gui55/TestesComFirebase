package com.example.appmaroto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmaroto.repository.UserRepository
import com.google.firebase.auth.AuthCredential

class LoginViewModel: ViewModel() {
    fun loginSuccess(email: String, password: String) = UserRepository.logIn(email, password)

    fun googleSignIn(authCredential: AuthCredential) = UserRepository.signInWithCredential(authCredential)

    fun isAnUserLoggedIn() = UserRepository.isAnUserLoggedIn()
}