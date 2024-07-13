package com.example.ecommerceapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.ui.activity.ShoppingActivity
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginBtnLogin.setOnClickListener {
                val email=emailEtLogin.text.toString().trim()
                val pass=passwordEtLogin.text.toString().trim()

                viewModel.loginUser(email,pass)


            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.login.collect{
                    when(it){
                        is Resource.Loading ->
                            binding.loginBtnLogin.startAnimation()
                        is Resource.Success ->{
                            binding.loginBtnLogin.revertAnimation()
                            Intent(requireActivity(),ShoppingActivity::class.java).also {
                                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(it)
                            }
                        }

                        is Resource.Error ->{
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            binding.loginBtnLogin.revertAnimation()
                        }
                            
                        else -> Unit

                    }
                }
            }
        }

    }


}