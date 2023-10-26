package com.slowAndSteady.slowdy.data.repository

import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.dao.UserDao
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun getUser(): Flow<UserEntity>{
        return userDao.getUser()
    }
    suspend fun createAndUpdateUser(userEntity: UserEntity){
        userDao.insertUser(userEntity)
    }

}