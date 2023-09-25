package getamaz.firebase.crud.ui.adapter

import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import getamaz.firebase.crud.R
import getamaz.firebase.crud.data.Note

class NotesAdapter : ListAdapter<Note, NotesAdapter.MyViewHolder>(MyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvRecyclerViewTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvRecyclerViewDescription)
        private val btnDelete: ImageView = itemView.findViewById(R.id.btnNoteDelete)
        fun bind(note: Note) {
            tvTitle.text = note.noteTitle
            tvDescription.text = note.noteDescription
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}