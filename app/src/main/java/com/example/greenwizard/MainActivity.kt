package com.example.greenwizard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import com.example.greenwizard.admin // Make sure to import the admin activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        // Bottom Navigation
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Navigate to the location selection fragment
                    navController.navigate(R.id.locationSelectionFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.news -> {
                    // Navigate to the news fragment
                    navController.navigate(R.id.userNews)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account -> {
                    val intent = Intent(this, admin::class.java)
                    startActivity(intent)
                }
            }
            false
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feedback, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        when (item.itemId) {
            R.id.Feedback -> {
                // Navigate to the Add Feedback fragment
                navController.navigate(R.id.addFeedback)
                return true
            }
            // Add more cases for other menu items if needed
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
