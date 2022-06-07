package com.app.memorista.domain.repositories

import com.app.memorista.domain.models.User

interface UserRepository {

    suspend fun get(): User

    suspend fun create(user: User)

    suspend fun update(user: User)

    suspend fun deleteAll()
}