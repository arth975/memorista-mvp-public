package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.dao.ListDao
import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.relations.ListWithTasksRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListLocalDataSource(
    private val listDao: ListDao
) : ListDataSource {

    override suspend fun fetchAllCategories(): Flow<List<ListEntity>> {
        var data: Flow<List<ListEntity>> = flow{}
        try {
            data = listDao.getAllListsFlow()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    override fun fetchCategoryWithTasksByCategoryId(id: Long): Flow<ListWithTasksRelation> =
        listDao.getListsWithTasksFlowById(id)

    override suspend fun fetchById(id: Long): ListEntity = listDao.getById(id)

    override suspend fun addList(listEntity: ListEntity): Long {
        return listDao.insertList(listEntity)
    }

    override suspend fun updateList(listEntity: ListEntity) {
        listDao.updateList(listEntity)
    }

    override suspend fun deleteList(listEntity: ListEntity) {
        listDao.deleteList(listEntity)
    }

}