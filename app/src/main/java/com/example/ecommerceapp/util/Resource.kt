package com.example.ecommerceapp.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T):Resource<T>(data)
    class Error<T>(msg:String):Resource<T>(message = msg)
    class Loading<T>:Resource<T>()
    class Idle<T>:Resource<T>()

}