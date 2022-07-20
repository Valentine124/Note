package com.valentine.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.valentine.note.databinding.ActivityNoteBinding
import com.valentine.note.db.Notes
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding

    private val noteViewModel : NoteViewModel by viewModels{
        NoteViewModelFactory((this.application as NoteApplication).noteRepository)
    }
    private var isNewNote = true
    private var isNoteSaved = false
    private lateinit var noteId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.noteToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.noteToolbar.setNavigationOnClickListener {
            this.onBackPressed()
        }

        val currentDateTime: Calendar = Calendar.getInstance()
        val cdDateFormat = SimpleDateFormat("h:mm a, MMM dd,yyy", Locale("English"))
        val dateTime = cdDateFormat.format(currentDateTime.time)
        binding.noteTime.text = dateTime

        val note = intent.getStringExtra(NOTE_EXTRA)
        val id = intent.getStringExtra(NOTE_ID_EXTRA)
        val date = intent.getStringExtra(NOTE_DATE_EXTRA)

        if (note != null && date != null && id != null) {
            isNewNote = false
            binding.noteBody.hideKeyBoard()
            binding.noteBody.text = note.toEditable()
            noteId = id

        } else {
            isNewNote = true
            binding.noteBody.postDelayed({ binding.noteBody.showKeyBoard() }, 100)
        }



    }

    private fun String.toEditable() : Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

    private fun EditText.showKeyBoard() {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun EditText.hideKeyBoard() {
        this.clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                if (isNewNote) {
                    saveNote()
                } else {
                    upDateNote()
                }
            }
            R.id.menu_delete -> {
                deleteNote()
            }
            R.id.menu_undo -> {
                binding.noteBody.text.clear()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val note = binding.noteBody.text.toString()
        val date = binding.noteTime.text.toString()
        val notes = Notes(noteId, note, date)
        noteViewModel.removeNote(notes)
        this.onBackPressed()
    }

    private fun upDateNote() {
        val note = binding.noteBody.text.toString()
        val date = binding.noteTime.text.toString()
        noteViewModel.updateNote(noteId, note, date)
        binding.noteBody.hideKeyBoard()
    }
    private fun saveNote() {
        isNoteSaved = true
        if (!TextUtils.isEmpty(binding.noteBody.text)) {

            val noteId = UUID.randomUUID().toString()
            val note = binding.noteBody.text.toString()
            val time = binding.noteTime.text.toString()

            val notes = Notes(noteId, note, time)
            noteViewModel.addNotes(notes)

            binding.noteBody.hideKeyBoard()
        }
    }


    override fun onPause() {
        super.onPause()
        if (isNewNote && !isNoteSaved) {
            saveNote()
        } else if (isNoteSaved) {
            super.onPause()
        } else {
            upDateNote()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.supportNavigateUpTo(Intent(this, NoteListFragment::class.java))
        return super.onSupportNavigateUp()
    }

}