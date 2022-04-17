package com.example.diaryapp.room

import androidx.room.*

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("UPDATE note SET title=:Title, description=:Description WHERE id=:Noid")
    suspend fun update(Title:String, Description:String, Noid:Int)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from note ORDER BY id ASC")
    suspend fun selectall() : MutableList<Note>

    @Query("SELECT * from note WHERE id=:Noid")
    suspend fun getNote(Noid: Int) : Note
}