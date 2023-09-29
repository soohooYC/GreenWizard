package com.example.greenwizard.fragments.add

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greenwizard.R
import com.example.greenwizard.model.Report
import com.example.greenwizard.viewmodel.LocationViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class addReport : Fragment() {

    // Initialize ViewModel
    private val locationViewModel: LocationViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var selectedImageBitmap: Bitmap? = null
    private lateinit var addImg: ImageView // Declare ImageView at the class level

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val intentData: Intent? = result.data
            intentData?.data?.let { uri ->
                selectedImageUri = uri
                try {
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    selectedImageBitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                // Display the selected image in your ImageView
                addImg.setImageBitmap(selectedImageBitmap)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_report, container, false)

        val addBtn = view.findViewById<Button>(R.id.addBtn)
        val editAddress = view.findViewById<EditText>(R.id.editTextAddress)
        val editdescription = view.findViewById<EditText>(R.id.editdescription)
        val edittypeofWaste = view.findViewById<EditText>(R.id.edittypeofWaste)
        addImg = view.findViewById(R.id.imageView)

        val selectImageButton = view.findViewById<Button>(R.id.ImgBtn)
        selectImageButton.setOnClickListener {
            selectImage()
        }

        addBtn.setOnClickListener {
            val address = editAddress.text.toString()
            val description = editdescription.text.toString()
            val typeofWaste = edittypeofWaste.text.toString()
            val status = "New"

            if (inputCheck(description, typeofWaste)) {
                // Check if an image is selected
                val imagePath = saveImageToInternalStorage(selectedImageBitmap)

                // Create report Object
                val report = Report(address,description, typeofWaste,status,imagePath) // Assuming 'id' is an auto-generated primary key // Pass imagePath as the third parameter
                // Add data to ViewModel
                locationViewModel.addReport(report)
                Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_addReport_to_listReport)
            } else {
                Toast.makeText(requireContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun inputCheck(description: String, typeofWaste: String): Boolean {
        return !(TextUtils.isEmpty(description) || TextUtils.isEmpty(typeofWaste))
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap?): String {
        if (bitmap == null) {
            return ""
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "JPEG_$timeStamp.jpg"

        val directory = ContextCompat.getExternalFilesDirs(requireContext(), null)[0]
        val imageFile = File(directory, fileName)

        try {
            val stream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return imageFile.absolutePath
    }
}