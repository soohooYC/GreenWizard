package com.example.greenwizard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView

    private lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Initialize UI elements
        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById(R.id.signupRedirectText)

        // Set a click listener for the login button
        loginButton.setOnClickListener {
            login()
        }

        // Set a click listener to redirect to the sign-up activity
        signupRedirectText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Sign in with email and password
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success
                    val currentUser = auth.currentUser
                    currentUser?.let { user ->
                        val userRef = database.child("users").child(user.uid)
                        userRef.child("role").addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val role = dataSnapshot.value as String?
                                val intent = when (role) {
                                    "admin" -> Intent(this@LoginActivity, admin::class.java)
                                    else -> Intent(this@LoginActivity, MainActivity::class.java)
                                }
                                startActivity(intent)
                                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle database error if needed
                            }
                        })
                    }
                } else {
                    // Login failed
                    Toast.makeText(this@LoginActivity, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                }
            }

    }
}


