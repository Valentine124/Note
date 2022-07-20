package com.valentine.note

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valentine.note.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {

    private var _binding: FragmentToDoListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.fab.setOnClickListener{
            val intent = Intent(requireContext(), NoteActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}