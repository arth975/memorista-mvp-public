package com.app.memorista.domain.usecases.user

class ValidateUserNameUseCase {

    operator fun invoke(name: String): Boolean {
        if(name.isEmpty())
            return false

        return true
    }
}