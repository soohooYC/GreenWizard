package com.example.greenwizard.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenwizard.R
import com.example.greenwizard.viewmodel.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class listNews : Fragment() {

    private lateinit var mNewsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_news, container, false)
        

        val floatingActionBtn = view.findViewById<FloatingActionButton>(R.id.floatingActionBtn)

        // RecyclerView
        val adapter = NewsAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // NewsViewModel
        mNewsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        mNewsViewModel.readAllData.observe(viewLifecycleOwner, Observer { newsList ->
            adapter.setData(newsList)
        })


        floatingActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listNews_to_addNews)
        }

        return view
    }
}
