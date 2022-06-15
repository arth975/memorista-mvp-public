package com.app.memorista.domain.usecases.user

import com.app.memorista.domain.models.User
import com.app.memorista.domain.repositories.UserRepository

class GetUserUseCase(private val repo: UserRepository) {

    suspend operator fun invoke(): User {
        return repo.get()
    }
}