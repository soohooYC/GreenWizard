package com.example.greenwizard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.greenwizard.data.LocationDatabase
import com.example.greenwizard.model.RecyclePoint
import com.example.greenwizard.repository.LocationRepository
import com.example.greenwizard.model.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Report>> // Change visibility to public
    val readAllRecycleData: LiveData<List<RecyclePoint>> // Change visibility to public
    private val repository: LocationRepository

    init {
        val locationDao = LocationDatabase.getDatabase(application).LocationDao()
        repository = LocationRepository(locationDao)
        readAllData = repository.readAllData
        readAllRecycleData = repository.readAllRecycleData
    }

    //Illegal Dump Report
    fun addReport(report: Report) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addReport(report)
        }
    }

    fun updateReport(report: Report) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReport(report)
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReport(report)
        }
    }

    //RecyclePoint
    fun addRecycle(recyclePoint: RecyclePoint) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecycle(recyclePoint)
        }
    }

    fun updateRecycle(recyclePoint: RecyclePoint) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecycle(recyclePoint)
        }
    }

    fun deleteRecycle(recyclePoint: RecyclePoint) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecycle(recyclePoint)
        }
    }
}
