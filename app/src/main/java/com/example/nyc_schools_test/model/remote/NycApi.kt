package com.example.nyc_schools_test.model.remote

import com.example.nyc_schools_test.common.END_POINT_SAT
import com.example.nyc_schools_test.common.END_POINT_SCHOOLS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NycApi {

    @GET(END_POINT_SCHOOLS)
    suspend fun getSchoolList(): Response<List<SchoolListResponse>>


    @GET(END_POINT_SAT)
    suspend fun getSchoolSat(): Response<List<SchoolSatResponse>>
}