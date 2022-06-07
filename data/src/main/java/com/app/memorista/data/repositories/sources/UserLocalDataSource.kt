package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.dao.UserDao
import com.app.memorista.data.local.entities.UserEntity

class UserLocalDataSource(
    private val userDao: UserDao
) : UserDataSource {
    override suspend fun getUser(): UserEntity = userDao.getUser()

    override suspend fun createUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

}