package com.app.toalarm.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.toalarm.data.local.dao.CategoryDao
import com.app.toalarm.data.local.dao.TaskDao
import com.app.toalarm.data.local.entities.CategoryEntity
import com.app.toalarm.data.local.entities.TaskEntity
import com.app.toalarm.data.utils.DateTimeConverter

/**
 * @ClassName: LocalDatabase
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:46 AM
 */
@Database(entities = [TaskEntity::class, CategoryEntity::class], version = 4, exportSchema = false)
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
                )//.createFromAsset("database/categories.db")
                    .addCallback(LocalDataBaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }
    }

    abstract fun getTaskDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao

    private class LocalDataBaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val cv = ContentValues().apply { put(CategoryEntity::name.name, "Default") }
            db.insert("categories", OnConflictStrategy.REPLACE, cv)
        }
    }
}