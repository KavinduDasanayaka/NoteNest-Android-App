package com.example.notenestapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notenestapp.model.Notes


@Database(entities = [Notes::class], version = 1)
    abstract class NoteDatabase:RoomDatabase(){

        abstract fun getNoteDao(): NoteDao

        companion object{
            //Make sure changes made by one thread can be seen by other
            @Volatile
            private var instance:NoteDatabase? = null
            //Lock ensure only one thread execute inside syn block and create a database instance
            private val LOCK = Any()

            //create notes database instance
            //singleton ensure that only one object is created
            operator fun invoke(context: Context) = instance ?:
            //Only one thread access
            synchronized(LOCK){
                instance ?:
                createDatabase(context).also{
                    instance = it
                }
            }

            private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_db"
                ).build()
        }
}