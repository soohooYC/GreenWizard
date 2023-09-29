package com.example.greenwizard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.content.pm.PackageManager
import android.view.MotionEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class admin: AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val navController = this.findNavController(R.id.adminnavHost)
        NavigationUI.setupActionBarWithNavController(this, navController)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                isReadPermissionGranted =
                    permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
                isWritePermissionGranted =
                    permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
            }
        requestPermission()

        //Bottom Nav
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Navigate to the location Select
                    navController.navigate(R.id.locationSelectionAdminragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.news -> {
                    // Navigate to the list News
                    navController.navigate(R.id.listNews)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account-> {
                    // Navigate to the ProfileFragment
                    navController.navigate(R.id.profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.adminnavHost)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //Close KeyBoard
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    //Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feedback, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.adminnavHost)
        when (item.itemId) {
            R.id.Feedback -> {
                // Navigate to the list Feedback fragment
                navController.navigate(R.id.listFeedback)
                return true
            }
            // Add more cases for other menu items if needed
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun requestPermission() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()

        if (!isReadPermissionGranted) {
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isWritePermissionGranted) {
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
}
