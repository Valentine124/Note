package com.valentine.note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentine.note.databinding.FragmentNoteListBinding
import com.valentine.note.db.Notes


class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null

    private val binding get() = _binding!!

    private val noteViewModel : NoteViewModel by viewModels{
        NoteViewModelFactory((requireActivity().application as NoteApplication).noteRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = NoteRecyclerAdapter()
        binding.noteRecyclerView.adapter = adapter
        binding.noteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.noteRecyclerView.scrollToPosition(0)

        binding.fab.setOnClickListener{
            startActivity(Intent(requireContext(), NoteActivity::class.java))
        }

        noteViewModel.notes.observe(viewLifecycleOwner) { note ->
            note?.let {
                adapter.submitList(it.reversed())
                adapter.setOnItemClickListener(object : NoteRecyclerAdapter.OnItemClickListener{
                    override fun onItemClickListener(note: Notes) {
                        val intent = Intent(requireContext(), NoteActivity::class.java)
                        intent.putExtra(NOTE_EXTRA, note.note)
                        intent.putExtra(NOTE_ID_EXTRA, note.noteId)
                        intent.putExtra(NOTE_DATE_EXTRA, note.dateTime)
                        startActivity(intent)
                    }
                })
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}