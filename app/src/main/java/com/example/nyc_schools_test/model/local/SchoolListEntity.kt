package com.example.nyc_schools_test.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SchoolListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "dbn")
    val dbn: String,
    @ColumnInfo(name = "school_name")
    val school_name: String,
    @ColumnInfo(name = "overview_paragraph")
    val overview_paragraph: String,
    @ColumnInfo(name = "neighborhood")
    val neighborhood: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "phone_number")
    val phone_number: String,
    @ColumnInfo(name = "school_email")
    val school_email: String?,
    @ColumnInfo(name = "website")
    val website: String,
)

@Entity
data class SchoolSatEntity(
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

