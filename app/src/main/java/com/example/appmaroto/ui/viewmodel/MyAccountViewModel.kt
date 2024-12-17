package com.example.appmaroto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmaroto.repository.UserRepository

class MyAccountViewModel: ViewModel() {
    fun getUserEmail(): String? = UserRepository.getCurrentUserEmail()
}