package com.example.tuitioncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tuitioncalculator.viewmodel.AuthState
import com.example.tuitioncalculator.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = view.findViewById<TextInputEditText>(R.id.etPassword)
        val btnSignIn = view.findViewById<MaterialButton>(R.id.btnSignIn)
        val tvSignUp = view.findViewById<View>(R.id.tvSignUp)
        val tvForgotPassword = view.findViewById<View>(R.id.tvForgotPassword)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }

        tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_forgotPassword)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.authState.collect { state ->
                        when (state) {
                            AuthState.AUTHENTICATED -> {
                                findNavController().navigate(R.id.action_login_to_home)
                            }
                            AuthState.LOADING -> {
                                btnSignIn.isEnabled = false
                                btnSignIn.text = "Signing In..."
                            }
                            else -> {
                                btnSignIn.isEnabled = true
                                btnSignIn.text = "Sign In"
                            }
                        }
                    }
                }
                
                launch {
                    authViewModel.errorMessage.collect { message ->
                        if (message != null && authViewModel.authState.value == AuthState.ERROR) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                            authViewModel.clearErrorMessage()
                        }
                    }
                }
            }
        }
    }
}
