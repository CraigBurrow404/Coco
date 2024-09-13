package com.burrow.sensorActivity2.dataInterface.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.burrow.sensorActivity2.dataInterface.Dao.SensorDao
import com.burrow.sensorActivity2.dataInterface.Entity.SensorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/* Database migrations are beyond the scope of this codelab, so exportSchema has been set to false
 here, in order to avoid a build warning. In a real app, consider setting a directory for Room to
 use to export the schema so you can check the current schema into your version control system.
 */

/* Note: When you modify the database schema, you'll need to update the version number and define a
 migration strategy.
 */

/* For example, a destroy and re-create strategy can be sufficient. But for a real app, you must
implement a migration strategy. See Understanding migrations with Room.
 */

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities =
    arrayOf(SensorEntity::class),
    version = 4,
    exportSchema = false)
public abstract class SensorRoomDatabase : RoomDatabase() {

    abstract fun selectSensorDao(): SensorDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SensorRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SensorRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
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
                // return instance
                instance
            }
        }

    }
    private class SelectSensorDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.selectSensorDao())
                }
            }
        }

        suspend fun populateDatabase(sensorDao: SensorDao) {
            // Delete all content here.
            sensorDao.deleteAll()

        }
    }
}