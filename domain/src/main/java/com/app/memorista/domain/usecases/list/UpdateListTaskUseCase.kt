package com.app.memorista.domain.usecases.list

abstract class UpdateListTaskUseCase {
    protected fun calculateCount(currentCount: Int, isIncremented: Boolean)
            : Int = if (isIncremented) currentCount + 1 else currentCount - 1
}