package com.app.memorista.data.local

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.memorista.data.local.dao.ListDao
import com.app.memorista.data.local.dao.TaskDao
import com.app.memorista.data.local.dao.UserDao
import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.TaskEntity
import com.app.memorista.data.local.entities.UserEntity
import com.app.memorista.data.utils.DateTimeConverter

/**
 * @ClassName: LocalDatabase
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:46 AM
 */
@Database(
    entities = [TaskEntity::class, ListEntity::class, UserEntity::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "com.app.toalarm.LOCAL_DB"
        private var instance: LocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    DB_NAME
                ).addCallback(LocalDatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }
    }

    abstract fun getTaskDao(): TaskDao
    abstract fun getUserDao(): UserDao
    abstract fun getCategoryDao(): ListDao

    private class LocalDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val cv = ContentValues().apply {
                put("name", "Default")
                put("tasksCount", 0)
                put("completedTasksPercent", 0)
                put("color", Color.BLUE)
            }
            db.insert("lists", OnConflictStrategy.REPLACE, cv)
        }
    }
}