package getamaz.firebase.crud.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import getamaz.firebase.crud.R
import getamaz.firebase.crud.databinding.ActivityMainBinding
import getamaz.firebase.crud.ui.adapter.NotesAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var database: DatabaseReference
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        functions()
    }

    private fun functions() {
        initializingLateinitProperties()
        settingUpRecyclerView()
    }
    private fun initializingLateinitProperties() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        database = Firebase.database.reference
        notesAdapter = NotesAdapter()
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