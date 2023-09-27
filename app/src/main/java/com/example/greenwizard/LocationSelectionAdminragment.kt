package com.example.greenwizard


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth



class LocationSelectionAdminragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_location_selection_adminragment, container, false)

        val rabutton = view.findViewById<Button>(R.id.reportadmin)
        val rpabutton = view.findViewById<Button>(R.id.recycle_Pointadmin)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        rabutton.setOnClickListener {
            view.findNavController().navigate(R.id.action_locationSelectionAdminragment_to_list_report_newFragment)
        }
        rpabutton.setOnClickListener{
            view.findNavController().navigate(R.id.action_locationSelectionAdminragment_to_listRecycle)
        }

        logoutButton.setOnClickListener{
            // Sign out the user from Firebase Authentication
            auth.signOut()

            // Create an Intent to navigate to the LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)

            // Start the LoginActivity
            startActivity(intent)

        }


        return view
    }

}