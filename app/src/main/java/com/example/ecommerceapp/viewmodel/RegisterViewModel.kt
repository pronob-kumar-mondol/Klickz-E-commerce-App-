package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.util.Constant.USER_COLLECTION
import com.example.ecommerceapp.util.RegisterFieldsState
import com.example.ecommerceapp.util.RegisterValidation
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.util.validateEmail
import com.example.ecommerceapp.util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db:FirebaseFirestore
):ViewModel() {

    private val _register= MutableStateFlow<Resource<User>>(Resource.Idle())
    val register:Flow<Resource<User>> = _register

    private val _validation= Channel<RegisterFieldsState>()
    val validation= _validation.receiveAsFlow()

    fun createUserWithEmailAndPassword(user:User,password: String) {

        viewModelScope.launch {

            _register.emit(Resource.Loading())

            if (validation(user.email,password)){

                firebaseAuth.createUserWithEmailAndPassword(user.email,password)
                    .addOnSuccessListener {
                        it.user?.let {
                            saveUserInfo(it.uid,user)
                        }
                    }.addOnFailureListener {
                        _register.value =Resource.Error(it.message.toString())
                    }
            }else{
                val registerFieldsState=RegisterFieldsState(
                    validateEmail(user.email),
                    validatePassword(password)
                )
                _validation.send(registerFieldsState)


            }
        }


    }

    private fun saveUserInfo(uid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                _register.value =Resource.Success(user)

            }.addOnFailureListener {
                _register.value =Resource.Error(it.message.toString())
            }

    }

    private fun validation(email: String, password: String): Boolean {

        val emailValidation= validateEmail(email)
        val passValidation= validatePassword(password)
        val validationResult=emailValidation is RegisterValidation.Sucsess && passValidation is RegisterValidation.Sucsess

        return validationResult
    }

}