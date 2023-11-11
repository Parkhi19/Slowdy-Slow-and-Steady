package com.slowAndSteady.slowdy.data.repository

import com.slowAndSteady.slowdy.SlowdyApplication
import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.dao.UserDao
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.data.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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

}