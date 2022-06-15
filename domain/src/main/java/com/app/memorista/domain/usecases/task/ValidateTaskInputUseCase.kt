package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.params.NewTaskParams
import java.time.LocalDateTime

class ValidateTaskInputUseCase {

    operator fun invoke(params: NewTaskParams): Boolean {
        if (params.title.isNullOrEmpty())
            return false
        if (params.listId == null || params.listId < 1)
            return false

        return true
    }
}