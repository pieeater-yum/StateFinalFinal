package org.unizd.rma.babic.database

import android.animation.TypeConverter
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.unizd.rma.babic.dao.TaskDao
import org.unizd.rma.babic.models.Task


@Database(
    entities = [Task::class],
    version = 4,
)
@TypeConverters(org.unizd.rma.babic.converter.TypeConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao : TaskDao

    companion object {

        private val migration_1_2 = object : Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
               // database.execSQL("ALTER TABLE Task ADD COLUMN imageData BLOB")
               // database.execSQL("ALTER TABLE Task ADD COLUMN imageUri TEXT NOT NULL DEFAULT ''")

            }


        }


        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_db"

                ).addMigrations(migration_1_2)
                    .build().also {
                    INSTANCE = it
                }
            }

        }
    }

}