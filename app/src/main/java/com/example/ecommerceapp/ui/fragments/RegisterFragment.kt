package com.example.ecommerceapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.databinding.FragmentRegisterBinding
import com.example.ecommerceapp.util.RegisterFieldsState
import com.example.ecommerceapp.util.RegisterValidation
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val TAG="RegisterActivity"
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            registerBtnRegister.setOnClickListener{
                val user=User(
                    firstNameEtRegister.text.toString().trim(),
                    lastNameEtRegister.text.toString().trim(),
                    emailEtRegister.text.toString().trim()
                )
                val password=passwordEtRegister.text.toString()
                Log.d(TAG, "onViewCreated: ${user.email}")
                Log.d(TAG, "hello: $password")

                viewModel.createUserWithEmailAndPassword(user,password)



            }

        }

        lifecycleScope.launch {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.registerBtnRegister.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.registerBtnRegister.revertAnimation()
                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        binding.registerBtnRegister.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            viewModel.validation.collect{
                if (it.email is RegisterValidation.Failed){
                    binding.emailEtRegister.apply {
                        withContext(Dispatchers.Main){
                            error=it.email.erorMsg
                        }
                    }
                }
                if (it.password is RegisterValidation.Failed){
                    binding.passwordEtRegister.apply {
                        withContext(Dispatchers.Main){
                            error=it.password.erorMsg
                        }
                    }
                }
            }
        }


    }
}