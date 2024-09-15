package com.burrow.sensorActivity2.dataInterface.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.burrow.sensorActivity2.dataInterface.dao.CaptureDao
import com.burrow.sensorActivity2.dataInterface.entity.CaptureEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CaptureEntity::class], version = 7, exportSchema = false)
abstract class CaptureRoomDatabase : RoomDatabase() {

    abstract fun dataCaptureDao(): CaptureDao

    companion object {
        @Volatile
        private var INSTANCE: CaptureRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CaptureRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaptureRoomDatabase::class.java,
                    "data_capture_database"
                )
                    .addCallback(DataCaptureDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DataCaptureDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dataCaptureDao())
                }
            }
        }

        suspend fun populateDatabase(captureDao: CaptureDao) {
            captureDao.deleteAll()
        }
    }
}