package com.slowAndSteady.slowdy.domain.useCase

import com.google.firebase.auth.FirebaseAuth
import com.slowAndSteady.slowdy.data.entity.UserEntity
import com.slowAndSteady.slowdy.data.repository.UserRepository
import com.slowAndSteady.slowdy.data.util.ResultState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
  private val userRepo : UserRepository
){
    suspend operator fun invoke(email : String, password : String, userName : String):ResultState {
        val isUserExist = userRepo.getUserFromRemoteByEmail(email).let {
            when(it){
                is ResultState.Success<*> -> true
                is ResultState.Error -> false
                else -> false
            }
        }
       if(isUserExist){
           return ResultState.Error(Exception("User already exist"))
       }
         val newUserAccount =  createAccount(email, password, userName)
        return when {
            newUserAccount.isSuccess -> {
                ResultState.Success(newUserAccount.getOrNull()!!)
            }
            else -> {
                ResultState.Error(newUserAccount.exceptionOrNull()!!)
            }
        }
    }

    suspend fun createAccount(email: String, password: String, userName : String)  = kotlin.runCatching {
        val auth = FirebaseAuth.getInstance()
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
       authResult.user?.let{
            it.sendEmailVerification()
            val userEntity = UserEntity(
                userName = userName,
                userID = it.uid,
                userEmail = it.email
            )
            userRepo.createAndUpdateUser(
                userEntity
            )
            userEntity
        }

    }


}

