package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var fAuth: FirebaseAuth
):ViewModel() {

    private val _login= MutableSharedFlow<Resource<FirebaseUser>>()
    val login= _login as SharedFlow<Resource<FirebaseUser>>


    fun loginUser(email:String,pass:String){

        viewModelScope.launch {
            _login.emit(Resource.Loading())
        }

        viewModelScope.launch {
             fAuth.signInWithEmailAndPassword(email,pass)
                 .addOnSuccessListener { it ->
                     it.user?.let {
                         viewModelScope.launch {
                             _login.emit(Resource.Success(it))
                         }
                     }
                 }.addOnFailureListener {
                    viewModelScope.launch {
                        _login.emit(Resource.Error(it.message.toString()))
                    }
                 }
        }
    }


}