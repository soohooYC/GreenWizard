package com.example.greenwizard

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greenwizard.viewmodel.NewsViewModel





class newsDetail : Fragment() {

    private lateinit var uNewsViewModel: NewsViewModel

    private val args by navArgs<newsDetailArgs>()
    // Initialize ViewModel
    private val newsViewModel: NewsViewModel by viewModels()

    // Initialize ImageView for displaying the selected image
    private lateinit var imgDetail: ImageView

    // Initialize URI to store the selected image URI
    private var selectedImageUri: Uri? = null
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news_detail, container, false)

          // Initialize mNewsViewModel
          uNewsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

          val titleDetail = view.findViewById<TextView>(R.id.titleDetail)
          val scrollDetail = view.findViewById<ScrollView>(R.id.scrollDetail)
          imgDetail = view.findViewById(R.id.imgDetail) // Initialize your ImageView
          val newsDescriptionTextView = view.findViewById<TextView>(R.id.newsDescriptionTextView)

          // Load the existing data
          titleDetail.text = args.currentDetails.title
          newsDescriptionTextView.text = args.currentDetails.newsDesc

          // Load and display the existing image if available
          if (!args.currentDetails.imagePath.isNullOrEmpty()) {
              try {
                  selectedImageUri = Uri.parse(args.currentDetails.imagePath)
                  imgDetail.setImageURI(selectedImageUri)
              } catch (e: Exception) {
                  // Handle any exceptions related to accessing the URI
                  e.printStackTrace()
              }
          }


          return view
    }



}