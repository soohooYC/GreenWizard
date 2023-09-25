package com.example.greenwizard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class LocationSelectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_location_selection, container, false)

        val rbutton = view.findViewById<Button>(R.id.Report)
        val rabutton = view.findViewById<Button>(R.id.reportadmin)
        val rpbutton = view.findViewById<Button>(R.id.recycle_Point)
        val rpabutton = view.findViewById<Button>(R.id.recycle_Pointadmin)

        rbutton.setOnClickListener {
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_listReport)
        }
        rabutton.setOnClickListener {
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_list_report_newFragment)
        }
        rpbutton.setOnClickListener{
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_list_recycle_userFragment)
        }
        rpabutton.setOnClickListener{
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_listRecycle)
        }

        return view
    }

}