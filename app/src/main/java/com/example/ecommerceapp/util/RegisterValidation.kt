package com.example.ecommerceapp.util

sealed class RegisterValidation {
    
    object Sucsess:RegisterValidation()
    data class Failed(val erorMsg:String):RegisterValidation()
    
}

data class RegisterFieldsState(
    val email:RegisterValidation,
    val password:RegisterValidation

)