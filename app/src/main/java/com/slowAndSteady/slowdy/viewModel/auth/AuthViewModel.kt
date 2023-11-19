package com.slowAndSteady.slowdy.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.slowAndSteady.slowdy.SlowdyApplication
import com.slowAndSteady.slowdy.data.entity.UserEntity
import com.slowAndSteady.slowdy.data.repository.HabitRepository
import com.slowAndSteady.slowdy.data.repository.UserRepository
import com.slowAndSteady.slowdy.data.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow(AuthState.NONE)
    val authState = _authState.asStateFlow()

    private val _userFetchFlow = MutableStateFlow<ResultState>(ResultState.None)
    val userFetchFlow = _userFetchFlow.asStateFlow()

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
   fun emailSignUp(email: String, password: String, userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = AuthState.LOADING
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                _authState.value = AuthState.SUCCESS
                viewModelScope.launch(Dispatchers.IO) {
                    userRepository.createAndUpdateUser(
                        userEntity = UserEntity(
                            userName =  userName,
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
    fun getUserFromRemote(userID: String) {
        _userFetchFlow.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUserFromRemote(userID)
            _userFetchFlow.value = user
        }
    }

    fun createUser(userName: String, email: String, uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.createAndUpdateUser(
                userEntity = UserEntity(
                    userName = userName,
                    userEmail = email,
                    userID = uid
                )
            )
        }
    }

    fun populateDataInLocal(uid: String) {
        SlowdyApplication.appScope.launch(Dispatchers.IO) {
            habitRepository.populateHabitsInLocal(uid)
        }
    }
}

enum class AuthState {
    SUCCESS, FAILED, LOADING, NONE
}