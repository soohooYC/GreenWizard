package com.example.greenwizard.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenwizard.viewmodel.NewsViewModel
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.greenwizard.R


class userNews : Fragment() {

    private lateinit var uNewsViewModel: NewsViewModel

       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user_news, container, false)

           // RecyclerView
           val adapter = newsAdapterUser()
           val recyclerView = view.findViewById<RecyclerView>(R.id.usernewsRecycle)
           recyclerView.adapter = adapter
           recyclerView.layoutManager = LinearLayoutManager(requireContext())

           // NewsViewModel
           uNewsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
           uNewsViewModel.readAllData.observe(viewLifecycleOwner, Observer { newsList ->
               adapter.setData(newsList)
           })



           return view
    }


}