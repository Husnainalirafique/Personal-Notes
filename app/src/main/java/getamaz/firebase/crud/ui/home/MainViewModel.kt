package getamaz.firebase.crud.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import getamaz.firebase.crud.data.Note
import getamaz.firebase.crud.utils.DataState

class MainViewModel : ViewModel() {
    private val database = Firebase.database.reference
    private val mutableLiveData = MutableLiveData<DataState<List<Note>>>()
    val getNotes: LiveData<DataState<List<Note>>> = mutableLiveData
    init {
        fetchNotes()
    }

    fun updateNote(title: String = "", description: String,id:String){
        val note = Note(title,description)
        database.child("Notes").child(id).setValue(note)
    }

    fun addNote(title: String = "", description: String) {
        val note = Note(title, description)
        database.child("Notes").push().setValue(note)
    }

    fun deleteAllNotes() {
        database.child("Notes").removeValue()
    }

    fun deleteById(id:String){
        database.child("Notes").child(id).removeValue()
    }

    private fun fetchNotes() {
        mutableLiveData.value = DataState.Loading
        database.child("Notes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(notes: DataSnapshot) {
                val noteList = mutableListOf<Note>()
                notes.children.sortedByDescending { it.key }.forEach { note ->
                    val id = note.key
                    val data = note.getValue(Note::class.java)
                    noteList.add(Note(data?.noteTitle, data?.noteDescription, id))
                }
                mutableLiveData.value = DataState.Success(noteList)
            }

            override fun onCancelled(error: DatabaseError) {
                mutableLiveData.value = DataState.Error(error.message)
            }
        })
    }
}