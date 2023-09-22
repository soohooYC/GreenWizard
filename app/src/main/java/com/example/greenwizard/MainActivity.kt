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
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher <Array<String>>
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
        }
        requestPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun requestPermission(){
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED

        val permissionRequest : MutableList<String> = ArrayList()

        if(!isReadPermissionGranted){
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
