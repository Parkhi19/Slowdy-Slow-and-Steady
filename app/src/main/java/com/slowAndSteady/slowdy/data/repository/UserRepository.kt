package com.slowAndSteady.slowdy.data.repository

import com.slowAndSteady.slowdy.SlowdyApplication
import com.slowAndSteady.slowdy.data.util.ResultState
import com.slowAndSteady.slowdy.data.dao.UserDao
import com.slowAndSteady.slowdy.data.entity.UserEntity
import com.slowAndSteady.slowdy.data.util.SlowdyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) : RemoteRepo {

    override val collectionName: String
        get() = "users"

    fun getUser(): Flow<UserEntity>{
        return userDao.getUser()
    }

    suspend fun createAndUpdateUser(userEntity: UserEntity){
        userDao.insertUser(userEntity)
        SlowdyApplication.appScope.launch(Dispatchers.IO) {
            collectionReference.document(userEntity.userID).set(userEntity)
        }
    }

    suspend fun getUserFromRemote(userID: String) : ResultState {
        val userQuery = collectionReference.whereEqualTo("userID", userID).limit(1).get().await()
        if(userQuery.isEmpty){
            return ResultState.Error(SlowdyException.UserNotFoundException)
        }
        val userEntity = userQuery.documents[0].toObject(UserEntity::class.java)
        userDao.insertUser(userEntity!!)
        return ResultState.Success(userEntity)
    }

    suspend fun getUserFromRemoteByEmail(email : String) :ResultState{
        val userQuery = collectionReference.whereEqualTo("userEmail", email).limit(1).get().await()
        if(userQuery.isEmpty){
            return ResultState.Error(SlowdyException.UserNotFoundException)
        }
        val userEntity = userQuery.documents[0].toObject(UserEntity::class.java)
        userDao.insertUser(userEntity!!)
        return ResultState.Success(userEntity)
    }
}