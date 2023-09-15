package com.example.greenwizard.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greenwizard.R
import com.example.greenwizard.data.News
import com.example.greenwizard.data.NewsViewModel

class addNews : Fragment() {

    // Initialize ViewModel
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_news, container, false)

        val addBtn = view.findViewById<Button>(R.id.addBtn)
        val editTitle = view.findViewById<EditText>(R.id.editTitle)
        val editDesc = view.findViewById<EditText>(R.id.editDesc)

        addBtn.setOnClickListener {
            val title = editTitle.text.toString()
            val newsDesc = editDesc.text.toString()

            if (inputCheck(title, newsDesc)) {
                // Create news Object
                val news = News(0, title, newsDesc) // Assuming 'id' is an auto-generated primary key
                // Add data to ViewModel
                newsViewModel.addNews(news)
                Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_addNews_to_listNews)
            } else {
                Toast.makeText(requireContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun inputCheck(title: String, newsDesc: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(newsDesc))
    }
}