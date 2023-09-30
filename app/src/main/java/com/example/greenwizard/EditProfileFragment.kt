package com.example.greenwizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfileFragment : Fragment() {

    private lateinit var editUsername: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var editPassword: EditText
    private lateinit var saveButton: Button

    private lateinit var usernameUser: String
    private lateinit var emailUser: String
    private lateinit var phoneUser: String
    private lateinit var passwordUser: String

    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        reference = FirebaseDatabase.getInstance().getReference("users")

        editUsername = view.findViewById(R.id.editUsername)
        editEmail = view.findViewById(R.id.editEmail)
        editPhone = view.findViewById(R.id.editPhone)
        editPassword = view.findViewById(R.id.editPassword)
        saveButton = view.findViewById(R.id.saveButton)

        // Retrieve user data passed from ProfileFragment
        usernameUser = arguments?.getString("username") ?: ""
        emailUser = arguments?.getString("email") ?: ""
        phoneUser = arguments?.getString("phone") ?: ""
        passwordUser = arguments?.getString("password") ?: ""

        // Populate EditText fields with user data
        editUsername.setText(usernameUser)
        editEmail.setText(emailUser)
        editEmail.isFocusable = false
        editEmail.isClickable = false
        editPhone.setText(phoneUser)
        editPassword.setText(passwordUser)

        saveButton.setOnClickListener {
            if (isNameChanged() || isPasswordChanged() || isPhoneChanged() ) {
                // Save changes to Firebase Realtime Database
                saveUserDataToDatabase()

                // Show a toast message indicating that changes are saved
                Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()

                if(isPasswordChanged()){
                    Toast.makeText(requireContext(), "Password Changed, please login again.", Toast.LENGTH_SHORT).show()
                }

                // Navigate back to the previous fragment (ProfileFragment)
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "No changes found", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun saveUserDataToDatabase() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            reference.child(uid).apply {
                child("username").setValue(editUsername.text.toString())
                child("email").setValue(editEmail.text.toString())
                child("phone").setValue(editPhone.text.toString())
                child("password").setValue(editPassword.text.toString())
                child("profilePassword").setValue(editPassword.text.toString())
            }
        }
    }

    private fun isNameChanged(): Boolean {
        val newName = editUsername.text.toString()
        return newName != usernameUser
    }

    private fun isPasswordChanged(): Boolean {
        val newPassword = editPassword.text.toString()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,}\$".toRegex()

        if (!passwordPattern.matches(newPassword)) {
            // Show a toast message for password validation error
            Toast.makeText(requireContext(), "Password must be at least 8 characters long and contain at least one letter, one digit, and one special character.", Toast.LENGTH_SHORT).show()
            return false
        } else if (newPassword != passwordUser) {
            user?.updatePassword(newPassword)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Password is updated successfully
                        Toast.makeText(
                            requireContext(),
                            "Password updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(
                            requireContext(),
                            "Please Login again",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Password update failed
                        Toast.makeText(
                            requireContext(),
                            "Failed to update password: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            return true // Password is valid and changed
        }

        return false // Password is not changed
    }

    private fun isPhoneChanged(): Boolean {
        val newPhone = editPhone.text.toString()
        val phonePattern = "^01\\d{8,9}\$".toRegex()

        if (!phonePattern.matches(newPhone)) {
            // Show a toast message for phone number validation error
            Toast.makeText(requireContext(), "Phone number must start with '01' and be 10 or 11 digits long.", Toast.LENGTH_SHORT).show()
            return false
        } else if (newPhone != phoneUser) {
            return true // Phone number is valid and changed
        }

        return false // Phone number is not changed
    }


}
