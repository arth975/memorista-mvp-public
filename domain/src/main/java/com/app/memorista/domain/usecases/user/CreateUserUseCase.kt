package com.app.memorista.domain.usecases.user

import com.app.memorista.domain.models.User
import com.app.memorista.domain.repositories.UserRepository

class CreateUserUseCase(private val repo: UserRepository) {

    suspend operator fun invoke(user: User) {
        repo.create(user)
    }
}