package com.app.memorista.data.local.dao

import androidx.room.*
import com.app.memorista.data.local.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE id = 1")
    suspend fun getUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}