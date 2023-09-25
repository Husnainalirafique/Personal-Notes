package getamaz.firebase.crud.ui.adapter


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import getamaz.firebase.crud.R
import getamaz.firebase.crud.data.Note
import getamaz.firebase.crud.ui.addNote.AddNoteActivity


class NotesAdapter(private val context:Context,private val callback: (String?) -> Unit) :
    ListAdapter<Note, NotesAdapter.ViewHolder>(NoteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvRecyclerViewTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvRecyclerViewDescription)
        private val btnDelete: ImageView = itemView.findViewById(R.id.btnNoteDelete)

        fun bind(note: Note) {
            tvTitle.text = note.noteTitle
            tvDescription.text = note.noteDescription
            btnDelete.setOnClickListener {
                callback.invoke(note.noteId)
            }
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.apply {
                    putString("title",note.noteTitle)
                    putString("des",note.noteDescription)
                    putString("id",note.noteId)
                }
                val intent = Intent(context,AddNoteActivity::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteTitle == newItem.noteTitle
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}
