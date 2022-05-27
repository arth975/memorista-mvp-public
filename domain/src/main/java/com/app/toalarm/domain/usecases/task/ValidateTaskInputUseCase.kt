package com.app.toalarm.domain.usecases.task

import com.app.toalarm.domain.models.params.NewTaskParams
import java.time.LocalDateTime

class ValidateTaskInputUseCase {

    operator fun invoke(params: NewTaskParams): Boolean {
        if (params.title.isNullOrEmpty())
            return false
        if (params.categoryId == null || params.categoryId < 1)
            return false
        if (params.taskDateTime == null || params.taskDateTime.isBefore(LocalDateTime.now())) {
            //TODO()
        }

        return true
    }
}