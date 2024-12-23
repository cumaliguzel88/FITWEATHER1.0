package com.cumaliguzel.apps.api
//T reference to weather model
sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String) : NetworkResponse<Nothing>()
    data object Loading : NetworkResponse<Nothing>()
}
