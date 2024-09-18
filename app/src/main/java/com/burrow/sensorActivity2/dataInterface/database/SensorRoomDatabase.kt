package com.burrow.sensorActivity2.dataInterface.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.burrow.sensorActivity2.dataInterface.dao.SensorDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities =
    [SensorEntity::class],
    version = 4,
    exportSchema = false)
abstract class SensorRoomDatabase : RoomDatabase() {

    abstract fun selectSensorDao(): SensorDao

    companion object {
        @Volatile
        private var INSTANCE: SensorRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SensorRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SensorRoomDatabase::class.java,
                    "select_sensor_database"
                )
                    .addCallback(SelectSensorDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
    private class SelectSensorDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.selectSensorDao())
                }
            }
        }

        suspend fun populateDatabase(sensorDao: SensorDao) {
            sensorDao.deleteAll()
        }
    }
}