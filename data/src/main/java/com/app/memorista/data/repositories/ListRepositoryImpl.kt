package com.app.memorista.data.repositories

import com.app.memorista.data.mappers.toDomain
import com.app.memorista.data.mappers.toEntity
import com.app.memorista.data.repositories.sources.ListDataSource
import com.app.memorista.domain.models.ListWithTasks
import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListRepositoryImpl(
    private val localDataSource: ListDataSource
) : ListRepository {

    override suspend fun getAll(): Flow<List<TaskList>> =
        localDataSource.fetchAllCategories().map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: Long): TaskList {
        return TaskList(0, "", 0, 0, 0)
    }

    override suspend fun getListWithTasksFlow(params: TaskParams): Flow<ListWithTasks> {
        return localDataSource
            .fetchCategoryWithTasksByCategoryId(params.listId)
            .map { categoryWithTasks ->
                ListWithTasks(
                    list = categoryWithTasks.list.toDomain(),
                    tasks = categoryWithTasks.tasks.map { it.toDomain() }
                )
            }
    }

    override suspend fun add(taskList: TaskList): Long {
        return localDataSource.addList(taskList.toEntity())
    }

    override suspend fun update(taskList: TaskList) {
        localDataSource.updateList(taskList.toEntity())
    }

    override suspend fun delete(taskList: TaskList) {
        localDataSource.deleteList(taskList.toEntity())
    }
}