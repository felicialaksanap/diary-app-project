package com.example.diaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryapp.room.Note
import com.example.diaryapp.room.NoteRoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var DB : NoteRoomDatabase

    private lateinit var adapterN: adapterNote
    private var arNote : MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DB = NoteRoomDatabase.getDatabase(this)

        adapterN = adapterNote(arNote)

        val _rvNotes = findViewById<RecyclerView>(R.id.rvNotes)
        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        _rvNotes.layoutManager = LinearLayoutManager(this)
        _rvNotes.adapter = adapterN

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        adapterN.setOnItemClickCallback(object: adapterNote.OnItemClickCallback {
            override fun delData(dtNote: Note) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.noteDao().delete(dtNote)
                    val note = DB.noteDao().selectall()
                    Log.d("data ROOM2", note.toString())

                    withContext(Dispatchers.Main) {
                        adapterN.isiData(note)
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val note = DB.noteDao().selectall()
            Log.d("data ROOM", note.toString())

            adapterN.isiData(note)
        }
    }
}