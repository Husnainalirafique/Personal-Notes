package getamaz.firebase.crud.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import getamaz.firebase.crud.data.Note

class MainViewModel : ViewModel() {
    private val database = Firebase.database.reference
    private val mutableLiveData = MutableLiveData<List<Note>>()

    init {
        fetchNotes()
    }

    fun getNotes(): LiveData<List<Note>> {
        return mutableLiveData
    }

    fun addNote(title: String, description: String) {
        val note = Note(title, description)
        database.child("Notes").push().setValue(note)
    }

    fun deleteAllNotes() {
        database.child("Notes").removeValue()
    }

    private fun fetchNotes() {
        database.child("Notes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(notes: DataSnapshot) {
                val noteList = mutableListOf<Note>()
                for (note in notes.children) {
                    val data = note.getValue(Note::class.java)
                    data?.let { noteList.add(it) }
                }
                mutableLiveData.value = noteList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}