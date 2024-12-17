package com.example.appmaroto.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmaroto.ResultStatus
import com.example.appmaroto.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import java.lang.Exception

object UserRepository {

    fun logIn(email: String, password: String): LiveData<ResultStatus<Boolean>> =
        MutableLiveData<ResultStatus<Boolean>>().apply {
            value = ResultStatus.Loading
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    value = ResultStatus.Success(true)
                }
                .addOnFailureListener { exception ->
                    value = ResultStatus.Error(getLoginErrorMessage(exception))
                }
        }

    fun isAnUserLoggedIn() = Firebase.auth.currentUser != null

    fun logout() = Firebase.auth.signOut()

    fun createUser(user: User): LiveData<ResultStatus<Boolean>> =
        MutableLiveData<ResultStatus<Boolean>>().apply {
            value = ResultStatus.Loading
            Firebase.auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener {
                    value = ResultStatus.Success(true)
                }
                .addOnFailureListener { exception ->
                    value = ResultStatus.Error(getRegisterErrorMessage(exception))
                }
        }

    fun getCurrentUserEmail(): String? = Firebase.auth.currentUser?.email

    fun signInWithCredential(credential: AuthCredential): LiveData<ResultStatus<Boolean>> =
        MutableLiveData<ResultStatus<Boolean>>().apply {
            value = ResultStatus.Loading
            Firebase.auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    value = ResultStatus.Success(true)
                }
                .addOnFailureListener { exception ->
                    value = ResultStatus.Error("Erro ao vincular conta Google com o Firebase")
                }
        }

    private fun getRegisterErrorMessage(exception: Exception): String =
        when (exception) {
            is FirebaseNetworkException -> "Sem conexão com a internet"
            is FirebaseAuthWeakPasswordException -> "Senha precisa de pelo menos 6 caracteres"
            is FirebaseAuthUserCollisionException -> "E-mail já cadastrado"
            is FirebaseAuthInvalidCredentialsException -> "E-mail inválido"
            else -> "Erro ao cadastrar usuário"
        }

    private fun getLoginErrorMessage(exception: Exception): String =
        when (exception) {
            is FirebaseAuthInvalidUserException,
            is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos"
            else -> "Erro ao fazer login"
        }
}