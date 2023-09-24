package com.example.greenwizard.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenwizard.R
import com.example.greenwizard.viewmodel.LocationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class list_report_newFragment : Fragment() {

    private lateinit var mLocationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_report_new, container, false)

        // RecyclerView
        val adapter = ReportNewAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // NewsViewModel
        mLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        mLocationViewModel.readNewReportData.observe(viewLifecycleOwner, Observer { reportList ->
            adapter.setData(reportList)
        })


        return view
    }

}