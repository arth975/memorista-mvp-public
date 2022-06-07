package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.usecases.user.CreateUserUseCase
import com.app.memorista.domain.usecases.user.ValidateUserNameUseCase
import com.app.memorista.mappers.toDomain
import com.app.memorista.models.UserUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class IntroViewModel(
    private val createUser: CreateUserUseCase,
    private val validateUserName: ValidateUserNameUseCase
) : ViewModel() {

    private val mInvalidInputMessage = "Please enter the correct name."

    private val mValidationFlow = MutableSharedFlow<UserValidation>(replay = 1)
    val validationFlow = mValidationFlow.asSharedFlow()

    fun validate(name: String) {
        validateUserName(name).also { isValid ->
            if (isValid)
                mValidationFlow.tryEmit(UserValidation(isValid = true, data = name))
            else
                mValidationFlow.tryEmit(UserValidation(isValid = false, data = mInvalidInputMessage))
        }

    }

    fun createUser(user: UserUI) {
        viewModelScope.launch {
            createUser(user.toDomain())
        }
    }

    data class UserValidation(
        val isValid: Boolean,
        val data: String = ""
    )
}