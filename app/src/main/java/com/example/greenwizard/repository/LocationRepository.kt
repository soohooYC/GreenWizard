package com.example.greenwizard.repository

import androidx.lifecycle.LiveData
import com.example.greenwizard.data.LocationDao
import com.example.greenwizard.model.RecyclePoint
import com.example.greenwizard.model.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository (private val LocationDao: LocationDao){

    val readAllData: LiveData<List<Report>> = LocationDao.readAllReportData()
    val readNewReportData: LiveData<List<Report>> = LocationDao.readNewReportData()
    val readApprovedReportData: LiveData<List<Report>> = LocationDao.readApprovedReportData()
    val readCompletedReportData: LiveData<List<Report>> = LocationDao.readCompletedReportData()
    val readDeclineReportData: LiveData<List<Report>> = LocationDao.readDeclineReportData()


    val readAllRecycleData: LiveData<List<RecyclePoint>> = LocationDao.readAllRecycleData()

    //Illegal Dump Report
    suspend fun addReport(report: Report){
        LocationDao.addReport(report)
    }

    suspend fun updateReport(report: Report) {
        LocationDao.updateReport(report)
    }

    suspend fun deleteReport(report: Report) {
        LocationDao.deleteReport(report)
    }

    //RecyclePoint
    suspend fun addRecycle(recyclePoint: RecyclePoint){
        LocationDao.addRecycle(recyclePoint)
    }

    suspend fun updateRecycle(recyclePoint: RecyclePoint) {
        LocationDao.updateRecycle(recyclePoint)
    }

    suspend fun deleteRecycle(recyclePoint: RecyclePoint) {
        LocationDao.deleteRecycle(recyclePoint)
    }
}

