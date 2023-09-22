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

        val rpbutton = view.findViewById<Button>(R.id.Recycle_Point)

        rbutton.setOnClickListener {
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_listReport)
        }

        rpbutton.setOnClickListener{
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_listRecycle)
        }

        return view
    }

}