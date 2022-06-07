package com.app.memorista.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.TaskEntity

data class ListWithTasksRelation(
    @Embedded val list: ListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId"
    )
    val tasks: List<TaskEntity>
)