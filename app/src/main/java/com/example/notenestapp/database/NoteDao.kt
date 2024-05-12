package com.example.notenestapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notenestapp.model.Notes

@Dao
interface NoteDao {
//    Replace existing data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //This function call from coroutine
    suspend fun insertNote(note:Notes)

    @Update
    suspend fun updateNote(note:Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Notes>>

    //If data is like noteTitle or noteDesc store them in Livedata containing list Notes entities
    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteDesc LIKE:query")
    // Query can be null if no search query is given
    fun searchNote(query: String?):LiveData<List<Notes>>


}