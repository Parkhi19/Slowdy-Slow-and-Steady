package com.slowAndSteady.slowdy.viewModel.auth

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    suspend fun signInWithEmail(email:String, password:String){
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val token = authResult.user?.getIdToken(false)?.await()
    }
}