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

class SignupFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFullName = view.findViewById<TextInputEditText>(R.id.etFullName)
        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = view.findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val btnSignUp = view.findViewById<MaterialButton>(R.id.btnSignUp)
        val tvSignIn = view.findViewById<View>(R.id.tvSignIn)

        btnSignUp.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.signup(email, password, name)
        }

        tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.authState.collect { state ->
                        when (state) {
                            AuthState.AUTHENTICATED -> {
                                // Save name to local prefs so ProfileFragment sees it
                                val prefs = requireContext().getSharedPreferences("UserProfilePrefs", android.content.Context.MODE_PRIVATE)
                                prefs.edit().putString("USER_NAME", etFullName.text.toString().trim()).apply()

                                findNavController().navigate(R.id.action_signup_to_home)
                            }
                            AuthState.LOADING -> {
                                btnSignUp.isEnabled = false
                                btnSignUp.text = "Creating Account..."
                            }
                            else -> {
                                btnSignUp.isEnabled = true
                                btnSignUp.text = "Sign Up"
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
