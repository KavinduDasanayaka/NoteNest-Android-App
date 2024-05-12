package com.example.notenestapp.repository

import com.example.notenestapp.database.NoteDatabase
import com.example.notenestapp.model.Notes

class NoteRepository(private val db : NoteDatabase){

    //suspend make sure all of them run in backgroud thread
    suspend fun insertNote(notes: Notes) = db.getNoteDao().insertNote(notes)
    suspend fun deleteNote(notes: Notes) = db.getNoteDao().deleteNote(notes)
    suspend fun updateNote(notes: Notes) = db.getNoteDao().updateNote(notes)


    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query:String?) = db.getNoteDao().searchNote(query)

}