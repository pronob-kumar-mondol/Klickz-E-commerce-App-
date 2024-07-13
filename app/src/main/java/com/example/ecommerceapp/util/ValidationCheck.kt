package com.example.ecommerceapp.util

import android.util.Patterns

fun validateEmail(email:String):RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email format")

    return RegisterValidation.Sucsess
}

fun validatePassword(pass:String):RegisterValidation{
    if (pass.isEmpty())
        return RegisterValidation.Failed("Password cannot be empty")
    if (pass.length<6)
        return RegisterValidation.Failed("Password should contain at least 6 characters")

    return RegisterValidation.Sucsess
}