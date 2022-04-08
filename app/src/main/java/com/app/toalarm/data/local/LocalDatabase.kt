package com.app.toalarm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.toalarm.data.local.dao.TaskDao
import com.app.toalarm.data.local.entities.Task
import com.app.toalarm.utils.db.type.converters.DateTimeConverter

/**
 * @ClassName: LocalDatabase
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:46 AM
 */
@Database(entities = [Task::class], version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class LocalDatabase: RoomDatabase() {

    companion object {
        private const val DB_NAME = "com.app.toalarm.LOCAL_DB"

        fun create(context: Context): LocalDatabase{
            return Room.databaseBuilder(
                context,
                LocalDatabase::class.java,
                DB_NAME
            ).build()
        }
    }

    abstract fun getTaskDao(): TaskDao
}