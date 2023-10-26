package com.slowAndSteady.slowdy.viewModel.auth

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.slowAndSteady.slowdy.data.entity.UserEntity
import com.slowAndSteady.slowdy.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow(AuthState.NONE)
    val authState = _authState.asStateFlow()

    suspend fun signInWithEmail(email: String, password: String) {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val token = authResult.user?.getIdToken(false)?.await()
    }

    fun anonymousSignIn(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = AuthState.LOADING
            val auth = FirebaseAuth.getInstance()
            auth.signInAnonymously().addOnSuccessListener {
                viewModelScope.launch(Dispatchers.IO) {
                    userRepository.createAndUpdateUser(
                        userEntity = UserEntity(
                            userName = userName,
                            userID = it.user?.uid.toString()
                        )
                    )
                    _authState.value = AuthState.SUCCESS
                }
            }.addOnFailureListener {
                _authState.value = AuthState.FAILED
            }
        }
    }
}

enum class AuthState {
    SUCCESS, FAILED, LOADING, NONE
}