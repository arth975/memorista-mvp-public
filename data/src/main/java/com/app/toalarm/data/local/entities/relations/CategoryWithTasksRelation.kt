package com.app.toalarm.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.toalarm.data.local.entities.CategoryEntity
import com.app.toalarm.data.local.entities.TaskEntity

data class CategoryWithTasksRelation(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val tasks: List<TaskEntity>
)