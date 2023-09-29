package com.example.greenwizard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.greenwizard.model.RecyclePoint
import com.example.greenwizard.model.Report

@Dao
interface LocationDao {

    //Illegal Dump Report
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReport(report: Report)

    @Update
    suspend fun updateReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Query(value = "Select * FROM report_table ORDER BY id ASC")
    fun readAllReportData(): LiveData<List<Report>>

    @Query(value = "SELECT * FROM report_table WHERE status = 'New' ORDER BY id ASC")
    fun readNewReportData(): LiveData<List<Report>>

    @Query(value = "SELECT * FROM report_table WHERE status = 'Approved' ORDER BY id ASC")
    fun readApprovedReportData(): LiveData<List<Report>>

    @Query(value = "SELECT * FROM report_table WHERE status = 'Completed' ORDER BY id ASC")
    fun readCompletedReportData(): LiveData<List<Report>>

    //RecyclePoint
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecycle(recyclePoint: RecyclePoint)

    @Update
    suspend fun updateRecycle(recyclePoint: RecyclePoint)

    @Delete
    suspend fun deleteRecycle(recyclePoint: RecyclePoint)

    @Query(value = "Select * FROM recyclePoint_table ORDER BY id ASC")
    fun readAllRecycleData(): LiveData<List<RecyclePoint>>

}