package com.example.greenwizard.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.greenwizard.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class listNews : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_news, container, false)

        val floatingActionBtn = view.findViewById<FloatingActionButton>(R.id.floatingActionBtn)

        floatingActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listNews_to_addNews)
        }

        return view
    }
}