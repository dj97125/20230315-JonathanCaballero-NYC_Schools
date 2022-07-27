package com.example.nyc_schools_test.model.remote

import android.util.Log
import com.example.nyc_schools_test.common.FailedResponseException
import com.example.nyc_schools_test.common.NullResponseException
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.SatDomain
import com.example.nyc_schools_test.domain.SchoolDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface RemoteDataSource {
    fun NYCSchoolCatched(): Flow<StateAction>
    fun NYCSatToRoom(): Flow<StateAction>
}

class RemoteDataSourceImpl @Inject constructor(
    private val service: NycApi
) : RemoteDataSource {
    override fun NYCSchoolCatched(): Flow<StateAction> = flow {
        val respose = service.getSchoolList()
        if (respose.isSuccessful) {
            respose.body()?.let { result ->
                emit(StateAction.SUCCESS(result.map { it.toDomainSchoolModel() }))
            } ?: throw NullResponseException()
        } else {
            throw FailedResponseException()
        }

    }

    override fun NYCSatToRoom(): Flow<StateAction> = flow {
        val respose = service.getSchoolSat()
        if (respose.isSuccessful) {
            respose.body()?.let { result ->
                emit(StateAction.SUCCESS(result.map { it.toDomainSatModel() }))
            } ?: throw NullResponseException()
        } else {
            throw FailedResponseException()
        }
    }

}

private fun List<SchoolListResponse>.toDomainSchoolModel(): List<SchoolDomain> = map {
    it.toDomainSchoolModel()
}

private fun SchoolListResponse.toDomainSchoolModel(): SchoolDomain =
    SchoolDomain(
        dbn,
        school_name,
        overview_paragraph,
        neighborhood,
        location,
        phone_number,
        school_email,
        website,
    )

private fun List<SchoolSatResponse>.toDomainSatModel(): List<SatDomain> = map {
    it.toDomainSatModel()
}

private fun SchoolSatResponse.toDomainSatModel(): SatDomain =
    SatDomain(
        id = 0,dbn, satTestTakers, readingAvg, mathAvg, writingAvg
    )
