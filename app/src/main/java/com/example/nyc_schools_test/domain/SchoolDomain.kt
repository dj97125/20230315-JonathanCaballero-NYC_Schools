package com.example.nyc_schools_test.domain

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SchoolDomain(
    val dbn: String,
    val school_name: String,
    val overview_paragraph: String,
    val neighborhood: String,
    val location: String,
    val phone_number: String,
    val school_email: String?,
    val website: String,
)


data class SatDomain(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "dbn")
    val dbn: String,
    @ColumnInfo(name = "satTestTakers")
    @SerializedName("num_of_sat_test_takers")
    val satTestTakers: String,
    @ColumnInfo(name = "readingAvg")
    @SerializedName("sat_critical_reading_avg_score")
    val readingAvg: String,
    @ColumnInfo(name = "mathAvg")
    @SerializedName("sat_math_avg_score")
    val mathAvg: String,
    @ColumnInfo(name = "writingAvg")
    @SerializedName("sat_writing_avg_score")
    val writingAvg: String
)
