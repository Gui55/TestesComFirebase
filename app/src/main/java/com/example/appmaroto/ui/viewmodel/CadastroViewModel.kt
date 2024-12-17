package com.example.appmaroto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmaroto.model.User
import com.example.appmaroto.repository.UserRepository

class CadastroViewModel: ViewModel() {
    fun cadastrarUsuario(user: User) = UserRepository.createUser(user)
}