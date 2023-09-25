package getamaz.firebase.crud.ui.addNote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import getamaz.firebase.crud.R
import getamaz.firebase.crud.databinding.ActivityAddNoteBinding
import getamaz.firebase.crud.ui.home.MainViewModel
import getamaz.firebase.crud.utils.isEditTextEmpty
import getamaz.firebase.crud.utils.setStatusBarAppearance
import getamaz.firebase.crud.utils.textFromEditText
import getamaz.firebase.crud.utils.toast

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        addNote()
        updateNote()
        handleBackPressed()
        setStatusBarAppearance(window.decorView.rootView)

    }

    private fun addNote() {
        binding.noteSaveButton.setOnClickListener {
            if (!isEditTextEmpty(binding.noteTitleEditText) && !isEditTextEmpty(binding.noteDescriptionEditText)) {
                viewModel.addNote(textFromEditText(binding.noteTitleEditText), textFromEditText(binding.noteDescriptionEditText))
                binding.apply {
                    noteTitleEditText.text.clear()
                    noteDescriptionEditText.text.clear()
                }
                finish()
            } else {
                toast("Fill Both fields!")
            }
        }
    }

    private fun updateNote() {
        intent.extras?.apply {
            binding.noteTitleEditText.setText(getString("title"))
            binding.noteDescriptionEditText.setText(getString("des"))
            val id = getString("id")
            binding.noteSaveButton.setOnClickListener {
                if (!isEditTextEmpty(binding.noteTitleEditText) && !isEditTextEmpty(binding.noteDescriptionEditText)){
                    viewModel.updateNote(textFromEditText(binding.noteTitleEditText), textFromEditText(binding.noteDescriptionEditText), id!!)
                    finish()
                }
                else {
                    toast("Fill Both fields!")
                }
            }
        }
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }

        })
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}