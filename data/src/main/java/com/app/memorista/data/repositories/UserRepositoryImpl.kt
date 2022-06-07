package com.app.memorista.data.repositories

import com.app.memorista.data.mappers.toDomain
import com.app.memorista.data.mappers.toEntity
import com.app.memorista.data.repositories.sources.UserDataSource
import com.app.memorista.domain.models.User
import com.app.memorista.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val dataSource: UserDataSource
) : UserRepository {
    override suspend fun get(): User = dataSource.getUser().toDomain()

    override suspend fun create(user: User) {
        dataSource.createUser(user.toEntity())
    }

    override suspend fun update(user: User) {
        dataSource.updateUser(user.toEntity())
    }

    override suspend fun deleteAll() {
        dataSource.deleteAllUsers()
    }
}