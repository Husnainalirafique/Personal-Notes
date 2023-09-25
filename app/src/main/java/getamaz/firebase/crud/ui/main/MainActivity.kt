package getamaz.firebase.crud.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import getamaz.firebase.crud.R
import getamaz.firebase.crud.databinding.ActivityMainBinding
import getamaz.firebase.crud.ui.adapter.NotesAdapter
import getamaz.firebase.crud.utils.isEditTextEmpty
import getamaz.firebase.crud.utils.textFromEditText
import getamaz.firebase.crud.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        functions()
    }

    private fun functions() {
        initializingLateinitProperties()
        settingUpRecyclerView()
        addNote()
        readNotes()
        deleteAllNotes()
    }
    private fun initializingLateinitProperties() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        notesAdapter = NotesAdapter()
    }

    private fun addNote(){
        binding.btnCreateNote.setOnClickListener {
            if (!isEditTextEmpty(binding.etNoteTitle) && !isEditTextEmpty(binding.etNoteDes)){
                viewModel.addNote(textFromEditText(binding.etNoteTitle),textFromEditText(binding.etNoteDes))
                binding.apply {
                    etNoteTitle.text.clear()
                    etNoteDes.text.clear()
                }
            }
            else{
                toast("Fill Both fields!")
            }
        }
    }

    private fun readNotes(){
        viewModel.getNotes().observe(this) { notes ->
            notes?.let {
                notesAdapter.submitList(notes)
            }
        }
    }

    private fun deleteAllNotes(){
       binding.btnDeleteAllNotes.setOnClickListener {
           viewModel.deleteAllNotes()
       }
    }

    private fun settingUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = notesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}