package com.example.greenwizard.fragments.update

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greenwizard.R
import com.example.greenwizard.model.Report
import com.example.greenwizard.viewmodel.LocationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class approved_reportFragment : Fragment() {


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
    ): View?
    {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_approved_report, container, false)

        // Initialize mNewsViewModel
        mReportViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        val updateaddress = view.findViewById<EditText>(R.id.editTextAddress)
        val updatedescription = view.findViewById<EditText>(R.id.editdescription)
        val updatetypeofWaste = view.findViewById<EditText>(R.id.edittypeofWaste)
        updateImg = view.findViewById(R.id.updateImg) // Initialize your ImageView
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

        deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                // Use your ViewModel to delete the news using the existing args
                val location = updateaddress.text.toString()
                val description = updatedescription.text.toString()
                val typeofWaste = updatetypeofWaste.text.toString()

                    // Check if an image is selected
                    val imagePath = updateImg?.toString() // Get the selected image URI as a string
                    var Rstatus = "Decline"
                    val updatedNews = Report(
                        args.currentReport.id,
                        location,
                        description,
                        typeofWaste,
                        System.currentTimeMillis(),
                        Rstatus
                    )
                    // Update data to ViewModel
                    reportViewModel.updateReport(updatedNews)

                    Toast.makeText(
                    requireContext(),
                    "Successfully Removed: ${args.currentReport.description}",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_approved_reportFragment_to_list_report_newFragment)
            }
            builder.setNegativeButton("No") { _, _ ->
                // Do nothing or dismiss the dialog
            }
            builder.setTitle("Delete ${args.currentReport.description}?")
            builder.setMessage("Are you sure you want to decline ${args.currentReport.description} in " +
                    "${args.currentReport.status}?")
            builder.create().show()
        }

        updateStatusBtn.setOnClickListener {
            val location = updateaddress.text.toString()
            val description = updatedescription.text.toString()
            val typeofWaste = updatetypeofWaste.text.toString()

                // Check if an image is selected
                val imagePath = updateImg?.toString() // Get the selected image URI as a string
                var Rstatus = "Approved"
                val updatedNews = Report(
                    args.currentReport.id,
                    location,
                    description,
                    typeofWaste,
                    System.currentTimeMillis(),
                    Rstatus
                )
                // Update data to ViewModel
                reportViewModel.updateReport(updatedNews)
                Toast.makeText(requireContext(), "Successfully update status to Approved", Toast.LENGTH_LONG)
                    .show()
                // Navigate Back

                findNavController().navigate(R.id.action_approved_reportFragment_to_list_report_newFragment)

        }

        return view
    }

}