package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.entities.UserEntity

interface UserDataSource {

    suspend fun getUser(): UserEntity

    suspend fun createUser(user: UserEntity)

    suspend fun updateUser(user: UserEntity)

    suspend fun deleteAllUsers()
}