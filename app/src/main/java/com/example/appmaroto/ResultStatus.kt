package com.example.appmaroto

sealed class ResultStatus<out T>{
    data object Loading: ResultStatus<Nothing>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val errorMessage: String) : ResultStatus<Nothing>()
}