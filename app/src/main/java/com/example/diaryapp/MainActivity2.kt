package com.example.diaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.diaryapp.helper.DateHelper.getCurrentDate
import com.example.diaryapp.room.Note
import com.example.diaryapp.room.NoteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivity2 : AppCompatActivity() {

    val DB = NoteRoomDatabase.getDatabase(this)
    var date: String = getCurrentDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val _etTitle = findViewById<EditText>(R.id.etTitle)
        val _etDescription = findViewById<EditText>(R.id.etDescription)
        val _btnAdd = findViewById<Button>(R.id.btnAdd)
        val _btnUpdate = findViewById<Button>(R.id.btnUpdate)

        var iID : Int = 0
        var iAddEdit : Int = 0

        iID = intent.getIntExtra("noteId", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            _btnAdd.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etTitle.isEnabled = true
        }
        else {
            _btnAdd.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etTitle.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val noteitem = DB.noteDao().getNote(iID)
                _etTitle.setText(noteitem.title)
                _etDescription.setText(noteitem.description)
            }
        }

        _btnAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.noteDao().insert(
                    Note(0,_etTitle.text.toString(), _etDescription.text.toString(), date)
                )
                finish()
            }
        }

        _btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.noteDao().update(
                    _etTitle.text.toString(),
                    _etDescription.text.toString(),
                    iID
                )
                finish()
            }
        }
    }
}