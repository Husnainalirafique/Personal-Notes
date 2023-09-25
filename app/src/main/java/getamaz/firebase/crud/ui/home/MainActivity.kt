package getamaz.firebase.crud.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import getamaz.firebase.crud.R
import getamaz.firebase.crud.databinding.ActivityMainBinding
import getamaz.firebase.crud.ui.adapter.NotesAdapter
import getamaz.firebase.crud.ui.addNote.AddNoteActivity
import getamaz.firebase.crud.utils.setStatusBarAppearance
import getamaz.firebase.crud.utils.startActivity
import kotlin.system.exitProcess

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
        //About notes
        addNote()
        readNotes()
        deleteAllNotes()
        //others
        setStatusBarAppearance(window.decorView.rootView)
        handleBackPressed()
    }

    private fun initializingLateinitProperties() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        notesAdapter = NotesAdapter(this) {
            deleteNoteById(it!!)
        }
    }

    private fun addNote() {
        binding.btnAddNote.setOnClickListener {
            startActivity(AddNoteActivity::class.java)
        }
    }

    private fun readNotes() {
        viewModel.getNotes().observe(this) { notes ->
            notes?.let {
                notesAdapter.submitList(notes)
            }
        }
    }

    private fun deleteAllNotes() {
        binding.btnDeleteAllNotes.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setMessage("Delete All!")
                setTitle("Delete")
                setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteAllNotes()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                setCancelable(false)
                create()
                show()
            }

        }
    }

    private fun deleteNoteById(id: String) {
        viewModel.deleteById(id)
    }

    private fun settingUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = notesAdapter
        }
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@MainActivity)
                with(builder) {
                    setMessage("Close application!")
                    setPositiveButton("Yes") { _, _ ->
                        exitProcess(0)
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    setCancelable(false)
                    create()
                    show()
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}