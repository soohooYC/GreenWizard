package com.example.greenwizard.fragments.update

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.greenwizard.R
import com.example.greenwizard.model.RecyclePoint
import com.example.greenwizard.viewmodel.LocationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpdateRecycleUserFragment : Fragment() {

    private lateinit var mRecycleViewModel: LocationViewModel

    private val args by navArgs<UpdateRecycleFragmentArgs>()

    // Initialize ImageView for displaying the selected image
    private lateinit var updateImg: ImageView

    // Initialize URI to store the selected image URI
    private var selectedImageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_recycle_user, container, false)

        // Initialize mNewsViewModel
        mRecycleViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        val updateaddress = view.findViewById<EditText>(R.id.editTextAddress)
        val updatename = view.findViewById<EditText>(R.id.editname)
        updateImg = view.findViewById(R.id.updateImg) // Initialize your ImageView

        // Load the existing data
        updatename.setText(args.currentRecycle.name)
        updateaddress.setText(args.currentRecycle.address)

        // Load and display the existing image if available
        if (!args.currentRecycle.imagePath.isNullOrEmpty()) {
            selectedImageUri = Uri.parse(args.currentRecycle.imagePath)
            updateImg.setImageURI(selectedImageUri)
        }

        return view

    }

}