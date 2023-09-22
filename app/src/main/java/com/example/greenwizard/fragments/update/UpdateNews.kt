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
import com.example.greenwizard.R
import com.example.greenwizard.model.News
import com.example.greenwizard.viewmodel.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpdateNews : Fragment() {

    private lateinit var mNewsViewModel: NewsViewModel

    private val args by navArgs<UpdateNewsArgs>()
    // Initialize ViewModel
    private val newsViewModel: NewsViewModel by viewModels()

    // Initialize ImageView for displaying the selected image
    private lateinit var updateImg: ImageView

    // Initialize URI to store the selected image URI
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_news, container, false)

        // Initialize mNewsViewModel
        mNewsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val updateTitle = view.findViewById<EditText>(R.id.updateTitle)
        val updateDesc = view.findViewById<EditText>(R.id.updateDesc)
        val updateBtn = view.findViewById<Button>(R.id.updateBtn)
        updateImg = view.findViewById(R.id.updateImg) // Initialize your ImageView
        val deleteBtn = view.findViewById<FloatingActionButton>(R.id.deleteBtn)

        // Load the existing data
        updateTitle.setText(args.currentNews.title)
        updateDesc.setText(args.currentNews.newsDesc)

        // Load and display the existing image if available
        if (!args.currentNews.imagePath.isNullOrEmpty()) {
            try {
                selectedImageUri = Uri.parse(args.currentNews.imagePath)
                updateImg.setImageURI(selectedImageUri)
            } catch (e: Exception) {
                // Handle any exceptions related to accessing the URI
                e.printStackTrace()
            }
        }

        // Register for result when an image is picked
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                // Set the selected image URI and display it in the ImageView
                selectedImageUri = uri
                updateImg.setImageURI(uri)
            }
        }

        // Trigger image selection when a button is clicked
        val selectImageButton = view.findViewById<Button>(R.id.uImgbtn)
        selectImageButton.setOnClickListener {
            getContent.launch("image/*") // Specify the MIME type for images
        }

        updateBtn.setOnClickListener() {
            val title = updateTitle.text.toString()
            val newsDesc = updateDesc.text.toString()

            if (inputCheck(title, newsDesc)) {
                // Check if an image is selected
                val imagePath = selectedImageUri?.toString() // Get the selected image URI as a string
                // Create news Object
                val updatedNews = News(args.currentNews.id, title, newsDesc, System.currentTimeMillis(),imagePath ?: "")
                // Update data to ViewModel
                newsViewModel.updateNews(updatedNews)
                Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_updateNews_to_listNews)
            } else {
                Toast.makeText(requireContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show()
            }
        }

        deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                // Use your ViewModel to delete the news using the existing args
                mNewsViewModel.deleteNews(args.currentNews)
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed: ${args.currentNews.title}",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_updateNews_to_listNews)
            }
            builder.setNegativeButton("No") { _, _ ->
                // Do nothing or dismiss the dialog
            }
            builder.setTitle("Delete ${args.currentNews.title}?")
            builder.setMessage("Are you sure you want to delete ${args.currentNews.title}?")
            builder.create().show()
        }
        return view
    }

    private fun inputCheck(title: String, newsDesc: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(newsDesc))
    }
}
