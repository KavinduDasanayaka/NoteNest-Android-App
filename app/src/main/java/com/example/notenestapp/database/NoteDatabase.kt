package com.example.notenestapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notenestapp.model.Notes

@Database(entities = [Notes::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        // Make sure changes made by one thread can be seen by others
        @Volatile
        private var instance: NoteDatabase? = null
        // Lock ensures only one thread executes inside the synchronized block to create a database instance
        private val LOCK = Any()

        // Create a notes database instance
        // Singleton ensures that only one object is created
        operator fun invoke(context: Context): NoteDatabase = instance
            ?: synchronized(LOCK) {
                instance ?: createDatabase(context).also {
                    instance = it
                }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            )
                // Add migration from version 1 to version 2
                .addMigrations(MIGRATION_1_2)
                .build()

        // Migration from version 1 to version 2
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the new column noteDate to the notes table
                database.execSQL("ALTER TABLE notes ADD COLUMN noteDate TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}
