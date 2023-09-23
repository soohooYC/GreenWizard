package com.example.greenwizard.fragments.add

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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greenwizard.R
import com.example.greenwizard.model.News
import com.example.greenwizard.viewmodel.NewsViewModel

class addNews : Fragment() {

    // Initialize ViewModel
    private val newsViewModel: NewsViewModel by viewModels()

    // Initialize URI to store the selected image URI
    private var selectedImageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_news, container, false)

        val addBtn = view.findViewById<Button>(R.id.addBtn)
        val editTitle = view.findViewById<EditText>(R.id.editTitle)
        val editDesc = view.findViewById<EditText>(R.id.editDesc)
        val addImg = view.findViewById<ImageView>(R.id.imageView) // Initialize your ImageView

        // Register for result when an image is picked
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                // Set the selected image URI and display it in the ImageView
                selectedImageUri = uri
                addImg.setImageURI(uri)
            }
        }


        // Trigger image selection when a button is clicked
        val selectImageButton = view.findViewById<Button>(R.id.ImgBtn)
        selectImageButton.setOnClickListener {
            getContent.launch("image/*") // Specify the MIME type for images
        }

        addBtn.setOnClickListener {
            val title = editTitle.text.toString()
            val newsDesc = editDesc.text.toString()

            if (inputCheck(title, newsDesc)) {
                // Check if an image is selected
                val imagePath = selectedImageUri?.toString() // Get the selected image URI as a string
                // Create news Object
                val news = News(title, newsDesc,imagePath ?: "") // Assuming 'id' is an auto-generated primary key // Pass imagePath as the third parameter
                // Add data to ViewModel
                newsViewModel.addNews(news)
                Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_addNews_to_listNews)
            } else {
                Toast.makeText(requireContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }


    private fun inputCheck(title: String, newsDesc: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(newsDesc))
    }


}