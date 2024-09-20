package com.burrow.sensorActivity2.dataInterface.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities =
    [CaptureEntity::class],
    version = 11,
    exportSchema = false)
abstract class CaptureRoomDatabase : RoomDatabase() {

    abstract fun captureDao(): CaptureDao

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
                    "capture_database"
                )
                    .addCallback(CaptureDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
    private class CaptureDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.captureDao())
                }
            }
        }

        suspend fun populateDatabase(captureDao: CaptureDao) {
            captureDao.deleteAll()
        }
    }
}