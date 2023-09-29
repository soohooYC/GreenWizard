package com.example.greenwizard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.greenwizard.loginsignup.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.navigation.fragment.findNavController


class ProfileFragment : Fragment() {

    private lateinit var titleUsernameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var editButton: Button
    private lateinit var deleteAccountButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")

        // Initialize UI elements
        titleUsernameTextView = view.findViewById(R.id.titleUsername)
        usernameTextView = view.findViewById(R.id.profileUsername)
        emailTextView = view.findViewById(R.id.profileEmail)
        phoneTextView = view.findViewById(R.id.profilePhone)
        passwordTextView = view.findViewById(R.id.profilePassword)
        editButton = view.findViewById(R.id.editProfileButton)
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton)


        editButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", usernameTextView.text.toString())
            bundle.putString("email", emailTextView.text.toString())
            bundle.putString("phone", phoneTextView.text.toString())
            bundle.putString("password", passwordTextView.text.toString())

            // Navigate to the EditProfileFragment and pass the data using arguments
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
        }

        deleteAccountButton.setOnClickListener {
            // Call a function to delete the user's account
            deleteUserAccount()
        }

        // Load and display user profile data
        loadUserProfile()

        return view
    }


    private fun loadUserProfile() {
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null) {
            val userRef = usersRef.child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Retrieve user data from the database
                        val userData = snapshot.getValue(User::class.java)

                        // Update the UI with the user data
                        if (userData != null) {
                            usernameTextView.text = userData.username
                            emailTextView.text = userData.email
                            phoneTextView.text = userData.phone
                            passwordTextView.text = userData.password
                            titleUsernameTextView.text = userData.username
                        } else {
                            // Handle the case where userData is null
                            // This could indicate a database structure mismatch
                            Toast.makeText(
                                requireContext(),
                                "User data is null",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Handle the case where the snapshot doesn't exist
                        // This could indicate that data is missing in the database
                        Toast.makeText(
                            requireContext(),
                            "User data not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                    // You can display an error message or take appropriate action here
                    Toast.makeText(
                        requireContext(),
                        "Database Error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun deleteUserAccount() {
        val user = FirebaseAuth.getInstance().currentUser

        // Check if a user is signed in
        if (user != null) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Confirm Account Deletion")
            builder.setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            builder.setPositiveButton("Delete") { _, _ ->
                // User confirmed account deletion

                // First, delete user data from the Realtime Database
                val uid = user.uid
                val userRef = usersRef.child(uid)
                userRef.removeValue() // This will delete the user's data
                // Then, delete the user's account
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Account deletion was successful
                            // You can navigate to a different screen or perform other actions here
                            Toast.makeText(
                                requireContext(),
                                "Account deleted successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Sign out the user
                            FirebaseAuth.getInstance().signOut()

                            // Start the LoginActivity and finish the current activity
                            val intent = Intent(requireActivity(), LoginActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            // Account deletion failed
                            Toast.makeText(
                                requireContext(),
                                "Failed to delete account: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            builder.setNegativeButton("Cancel") { _, _ ->
                // User canceled account deletion, do nothing
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            // No user is currently signed in
            Toast.makeText(
                requireContext(),
                "No user is currently signed in",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}
