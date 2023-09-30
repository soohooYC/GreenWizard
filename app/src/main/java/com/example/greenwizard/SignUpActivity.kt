package com.example.greenwizard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.greenwizard.loginsignup.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginRedirectText: TextView

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Initialize UI elements
        usernameEditText = findViewById(R.id.signup_username)
        emailEditText = findViewById(R.id.signup_email)
        passwordEditText = findViewById(R.id.signup_password)
        phoneEditText = findViewById(R.id.signup_phone)
        confirmPasswordEditText = findViewById(R.id.signup_password2)
        signUpButton = findViewById(R.id.signup_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)

        // Set a click listener for the sign-up button
        signUpButton.setOnClickListener {
            signUp()
        }

        // Set a click listener to redirect to the login activity
        loginRedirectText.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun signUp() {
        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Validate input
        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Password validation: At least 8 characters with a combination of letters, numbers, and symbols.
        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,}\$".toRegex()
        if (!passwordPattern.matches(password)) {
            Toast.makeText(
                this,
                "Password must contain at least 8 characters with letters, numbers, and symbols.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Phone number validation for Malaysia (assuming the format is "+60xxxxxxxxx")
        val phonePattern = "^01\\d{8,9}\$".toRegex()
        if (!phonePattern.matches(phone)) {
            Toast.makeText(
                this,
                "Invalid Malaysian phone number. Please use the format +60xxxxxxxxx",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Attempt to create a new user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { signUpTask ->
                if (signUpTask.isSuccessful) {
                    // Sign-up success, add user data to Firebase Realtime Database
                    val user = auth.currentUser
                    val uid = user?.uid

                    if (uid != null) {
                        val database = FirebaseDatabase.getInstance()
                        val usersRef = database.getReference("users")
                        val userData = User(username, email, "user", phone, password)

                        usersRef.child(uid).setValue(userData)
                    }

                    startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    finish()

                    Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                } else {
                    // Check if the email is already in use
                    if (signUpTask.exception?.message?.contains("email address is already in use") == true) {
                        Toast.makeText(this, "Email address is already in use. Please use a different email.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Sign-up failed for other reasons
                        Toast.makeText(this, "Sign-up failed. Please try again later.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}


