package com.example.appmaroto.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmaroto.DataStoreConfig
import com.example.appmaroto.model.UserDevice
import com.example.appmaroto.repository.DeviceRepository
import com.example.appmaroto.repository.UserRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class LoginViewModel(
    private val deviceRepository: DeviceRepository
): ViewModel() {

    fun loginSuccess(email: String, password: String) = UserRepository.logIn(email, password)

    fun googleSignIn(authCredential: AuthCredential) = UserRepository.signInWithCredential(authCredential)

    fun isAnUserLoggedIn() = UserRepository.isAnUserLoggedIn()

    fun checkDeviceTokenStatus(context: Context) {
        viewModelScope.launch {
            DataStoreConfig.wasTokenSent(context).let { wasTokenSent ->
                if(!wasTokenSent){
                    FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                        viewModelScope.launch {
                            deviceRepository.registerDevice(UserDevice(token = token), context)
                        }
                    }
                }
            }
        }
    }
}