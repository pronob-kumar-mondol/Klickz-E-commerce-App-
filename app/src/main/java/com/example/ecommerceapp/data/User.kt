package com.example.ecommerceapp.data

class User(
    val firstNmae:String,
    val lastName:String,
    val email:String,
    val imagePath:String= ""
) {
    constructor():this("","","","")
}