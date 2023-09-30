package com.example.greenwizard.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenwizard.R
import com.example.greenwizard.viewmodel.LocationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class listReport : Fragment() {

    private lateinit var mLocationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_report, container, false)

        val floatingActionBtn = view.findViewById<FloatingActionButton>(R.id.floatingActionBtn)

        val FilterReportbtn = view.findViewById<FloatingActionButton>(R.id.FilterReportbtn)

        // RecyclerView
        val adapter = ReportAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        mLocationViewModel.readAllData.observe(viewLifecycleOwner, Observer { reportList ->
            adapter.setData(reportList)
        })

        floatingActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listReport_to_addReport)
        }

        var clickCount = 0

        FilterReportbtn.setOnClickListener {

            clickCount++

        when (clickCount) {
                1 -> {
                    mLocationViewModel.readNewReportData.observe(
                        viewLifecycleOwner,
                        Observer { reportList ->
                            adapter.setData(reportList)
                        })

                    Toast.makeText(requireContext(), "New Report", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    mLocationViewModel.readApprovedReportData.observe(
                        viewLifecycleOwner,
                        Observer { reportList ->
                            adapter.setData(reportList)
                        })

                    Toast.makeText(requireContext(), "Approved Report", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    mLocationViewModel.readCompletedReportData.observe(
                        viewLifecycleOwner,
                        Observer { reportList ->
                            adapter.setData(reportList)
                        })

                    Toast.makeText(requireContext(), "Completed Report", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    mLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
                    mLocationViewModel.readAllData.observe(viewLifecycleOwner, Observer { reportList ->
                        adapter.setData(reportList)
                    })
                    Toast.makeText(requireContext(), "All Report", Toast.LENGTH_SHORT).show()

                    clickCount = 0 // Reset clickCount to 0
                }
            }

        }

        return view
    }
}
