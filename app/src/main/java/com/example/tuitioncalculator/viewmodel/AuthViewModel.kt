package com.example.tuitioncalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AuthState {
    IDLE,
    LOADING,
    AUTHENTICATED,
    UNAUTHENTICATED,
    ERROR
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow(AuthState.IDLE)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.AUTHENTICATED
        } else {
            _authState.value = AuthState.UNAUTHENTICATED
        }
    }

    fun login(email: String, pass: String) {
        _authState.value = AuthState.LOADING
        _errorMessage.value = null
        
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.AUTHENTICATED
                } else {
                    _errorMessage.value = task.exception?.message ?: "Login failed"
                    _authState.value = AuthState.ERROR
                }
            }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun signup(email: String, pass: String, name: String) {
        _authState.value = AuthState.LOADING
        _errorMessage.value = null

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    val profileTask = user?.updateProfile(profileUpdates)
                    
                    if (profileTask != null) {
                        profileTask.addOnCompleteListener {
                            _authState.value = AuthState.AUTHENTICATED
                        }
                    } else {
                        _authState.value = AuthState.AUTHENTICATED
                    }
                } else {
                    _errorMessage.value = task.exception?.message ?: "Signup failed"
                    _authState.value = AuthState.ERROR
                }
            }
    }

    fun resetPassword(email: String) {
        _authState.value = AuthState.LOADING
        _errorMessage.value = null

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.IDLE // Reset to idle after sending
                    _errorMessage.value = "Reset link sent to email"
                } else {
                    _errorMessage.value = task.exception?.message ?: "Failed to send reset link"
                    _authState.value = AuthState.ERROR
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.UNAUTHENTICATED
    }
}
