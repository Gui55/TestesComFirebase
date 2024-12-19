package com.example.appmaroto.model

import android.os.Build

data class UserDevice(
    val marca: String = Build.BRAND,
    val modelo: String = Build.MODEL,
    val token: String
)
