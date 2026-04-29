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

class ForgotPasswordFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val btnResetPassword = view.findViewById<MaterialButton>(R.id.btnResetPassword)
        val tvSignIn = view.findViewById<View>(R.id.tvSignIn)

        btnResetPassword.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                authViewModel.resetPassword(email)
            } else {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
            }
        }

        tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPassword_to_login)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.authState.collect { state ->
                        when (state) {
                            AuthState.LOADING -> {
                                btnResetPassword.isEnabled = false
                                btnResetPassword.text = "Sending..."
                            }
                            else -> {
                                btnResetPassword.isEnabled = true
                                btnResetPassword.text = "Send Reset Link"
                            }
                        }
                    }
                }

                launch {
                    authViewModel.errorMessage.collect { message ->
                        if (message != null) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                            if (message == "Reset link sent to email") {
                                findNavController().navigate(R.id.action_forgotPassword_to_login)
                            }
                            authViewModel.clearErrorMessage()
                        }
                    }
                }
            }
        }
    }
}
