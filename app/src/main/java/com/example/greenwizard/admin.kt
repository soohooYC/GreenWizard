package com.example.greenwizard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.view.MotionEvent
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import android.view.MenuItem

import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.google.firebase.auth.FirebaseAuth

class admin: AppCompatActivity() , EasyPermissions.PermissionCallbacks{
    private lateinit var menu: Menu
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val navController = this.findNavController(R.id.adminnavHost)
        NavigationUI.setupActionBarWithNavController(this, navController)


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
        this.menu = menu // Assign the menu to the class-level variable
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
            MainActivity.PERMISSION_REQUEST_CODE,
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
