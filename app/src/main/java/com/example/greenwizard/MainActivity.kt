package com.example.greenwizard

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() , EasyPermissions.PermissionCallbacks  {
    private lateinit var menu: Menu

    private lateinit var auth: FirebaseAuth
    companion object{
        const val PERMISSION_REQUEST_CODE = 1
    }
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
                    // Navigate to the ProfileFragment
                    navController.navigate(R.id.profileFragment)
                    return@setOnNavigationItemSelectedListener true
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
        this.menu = menu // Assign the menu to the class-level variable
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
            R.id.Permission -> {
                requestPermission() // Call the requestPermission function here
                return true
            }
            R.id.Logout -> {
                // Sign out the user from Firebase Authentication
                auth.signOut()

                // Create an Intent to navigate to the LoginActivity
                val intent = Intent(this, LoginActivity::class.java)

                // Start the LoginActivity
                startActivity(intent)
                return true
            }

            // Add more cases for other menu items if needed
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
            val permissionMenuItem = menu.findItem(R.id.Permission)
            permissionMenuItem.isVisible = true
        } else {
            requestPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        // Permission granted, hide the "Permission" menu item
        val permissionMenuItem = menu.findItem(R.id.Permission)
        permissionMenuItem.isVisible = false
    }



    private fun hasPermission(){
        EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private fun requestPermission() {
        EasyPermissions.requestPermissions(
            this,
            "These permission are required for this application",
            PERMISSION_REQUEST_CODE,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions, grantResults, this)
    }
}
