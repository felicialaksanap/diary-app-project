package com.example.diaryapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diaryapp.room.Note

class adapterNote (private val listNote: MutableList<Note>) :
    RecyclerView.Adapter<adapterNote.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val _tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val _tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val _tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        val _ibEdit = itemView.findViewById<ImageButton>(R.id.ibEdit)
        val _ibDelete = itemView.findViewById<ImageButton>(R.id.ibDelete)
    }

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtNote: Note)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun isiData(list: List<Note>) {
        listNote.clear()
        listNote.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_details, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var note = listNote[position]
        holder._tvTitle.setText(note.title)
        holder._tvDescription.setText(note.description)
        holder._tvDate.setText(note.date)

        holder._ibEdit.setOnClickListener {
            val intent = Intent(it.context, MainActivity2::class.java)
            intent.putExtra("noteId", note.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._ibDelete.setOnClickListener {
            onItemClickCallback.delData(note)
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }


}