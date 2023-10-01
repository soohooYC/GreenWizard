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
import com.example.greenwizard.model.Report
import com.example.greenwizard.viewmodel.LocationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpdateReport : Fragment() {

    private lateinit var mReportViewModel: LocationViewModel

    private val args by navArgs<UpdateReportArgs>()
    // Initialize ViewModel
    private val reportViewModel: LocationViewModel by viewModels()

    // Initialize ImageView for displaying the selected image
    private lateinit var updateImg: ImageView

    // Initialize URI to store the selected image URI
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_report, container, false)

        // Initialize mReportViewModel
        mReportViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        val updateaddress = view.findViewById<EditText>(R.id.editTextAddress)
        val updatedescription = view.findViewById<EditText>(R.id.editdescription)
        val updatetypeofWaste = view.findViewById<EditText>(R.id.edittypeofWaste)
        val updateBtn = view.findViewById<Button>(R.id.updateBtn)
        updateImg = view.findViewById(R.id.updateImg) // Initialize your ImageView
        val uImgbtn = view.findViewById<Button>(R.id.uImgbtn)
        val deleteBtn = view.findViewById<FloatingActionButton>(R.id.deleteBtn)
        val updateStatusBtn = view.findViewById<Button>(R.id.updateStatusBtn)

        // Load the existing data
        updateaddress.setText(args.currentReport.address)
        updatedescription.setText(args.currentReport.description)
        updatetypeofWaste.setText(args.currentReport.typeofWaste)

        // Load and display the existing image if available
        if (!args.currentReport.imagePath.isNullOrEmpty()) {
            selectedImageUri = Uri.parse(args.currentReport.imagePath)
            updateImg.setImageURI(selectedImageUri)
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

        if(!args.currentReport.status.equals("Approved")){
            updateStatusBtn.visibility = View.GONE
        }

        if(args.currentReport.status.equals("Approved")){
            updateaddress.isEnabled = false
            updatedescription.isEnabled = false
            updatetypeofWaste.isEnabled = false
            updateBtn.visibility = View.GONE
            uImgbtn.visibility = View.GONE
        }

        updateBtn.setOnClickListener() {

            val address = updateaddress.text.toString()
            val description = updatedescription.text.toString()
            val typeofWaste = updatetypeofWaste.text.toString()
            val status = args.currentReport.status.toString()
            if (inputCheck(description, typeofWaste)) {
                // Check if an image is selected
                val imagePath = selectedImageUri?.toString() // Get the selected image URI as a string
                // Create Reports Object
                val updatedReports = Report(args.currentReport.id, address, description, typeofWaste, System.currentTimeMillis(),
                    status,imagePath ?: "")
                // Update data to ViewModel
                reportViewModel.updateReport(updatedReports)
                Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_updateReport_to_listReport)
            } else {
                Toast.makeText(requireContext(), "Please Fill Out All Fields And Ensure Description Fields Not Over 20 Words", Toast.LENGTH_LONG).show()
            }
        }

        deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                // Use your ViewModel to delete the Reports using the existing args
                mReportViewModel.deleteReport(args.currentReport)
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed: ${args.currentReport.description}",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_updateReport_to_listReport)
            }
            builder.setNegativeButton("No") { _, _ ->
                // Do nothing or dismiss the dialog
            }
            builder.setTitle("Delete ${args.currentReport.description}?")
            builder.setMessage("Are you sure you want to delete ${args.currentReport.description} ?")
            builder.create().show()
        }

        updateStatusBtn.setOnClickListener {
            val location = updateaddress.text.toString()
            val description = updatedescription.text.toString()
            val typeofWaste = updatetypeofWaste.text.toString()

            if (inputCheck(description, typeofWaste)) {
                // Check if an image is selected
                val imagePath = selectedImageUri?.toString() // Get the selected image URI as a string
                var Rstatus = "Completed"
                val updatedReports = Report(
                    args.currentReport.id,
                    location,
                    description,
                    typeofWaste,
                    System.currentTimeMillis(),
                    Rstatus,
                    imagePath ?: ""
                )
                // Update data to ViewModel
                reportViewModel.updateReport(updatedReports)
                Toast.makeText(requireContext(), "Successfully update status to Approved", Toast.LENGTH_LONG)
                    .show()
                // Navigate Back

                findNavController().navigate(R.id.action_updateReport_to_listReport)
            } else {
                Toast.makeText(requireContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun inputCheck(description: String, typeofWaste: String): Boolean {
        fun isNotEmpty(str: String) = str.isNotBlank()
        fun wordCount(str: String) = str.split("\\s+".toRegex()).size

        return isNotEmpty(description) &&
                isNotEmpty(typeofWaste) &&
                wordCount(description) <= 20
    }
}
