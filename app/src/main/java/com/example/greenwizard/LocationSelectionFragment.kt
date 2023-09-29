package com.example.greenwizard


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent



class LocationSelectionFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_location_selection, container, false)

        val rbutton = view.findViewById<Button>(R.id.Report)
        val rpbutton = view.findViewById<Button>(R.id.recycle_Point)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        rbutton.setOnClickListener {
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_listReport)
        }
        rpbutton.setOnClickListener{
            view.findNavController().navigate(R.id.action_locationSelectionFragment_to_list_recycle_userFragment)
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