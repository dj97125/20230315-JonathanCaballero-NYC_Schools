package com.example.nyc_schools_test.model.remote

import android.util.Log
import com.example.nyc_schools_test.common.FailedResponseException
import com.example.nyc_schools_test.common.InternetCheck
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.SatDomain
import com.example.nyc_schools_test.domain.SchoolDomain
import com.example.nyc_schools_test.model.local.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface Repository {
    fun NYCSchoolCatched(): Flow<StateAction>
    fun NYCSatCatched(schoolDbn: String): Flow<StateAction>
}

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override fun NYCSchoolCatched(): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.NYCSchoolCatched()
        if (connected.isConnected()) {
            remoteService.collect() { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedSchools = stateAction.response as List<SchoolDomain>
                        emit(StateAction.SUCCESS(retrievedSchools))
                        localDataSource.insertLocalSChool(retrievedSchools).collect()

                    }
                }
            }
        } else {
            val cache = localDataSource.getAllCachedSchools()
            cache.collect(){ stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedSchools = stateAction.response as List<SchoolDomain>
                        emit(StateAction.SUCCESS(retrievedSchools))
                    }
                }
            }
        }
    }

    override fun NYCSatCatched(schoolDbn: String): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.NYCSatToRoom()
        if (connected.isConnected()) {
            remoteService.collect() { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedSat = stateAction.response as List<SatDomain>
                        localDataSource.insertLocalSat(retrievedSat).collect()
                        emit(StateAction.SUCCESS(retrievedSat.filter { x ->
                            x.dbn == schoolDbn
                        }))
                    }
                }
            }
        } else {
            val cache = localDataSource.getAllCachedSat()
            cache.collect(){ stateAction ->
                when (stateAction){
                    is StateAction.SUCCESS<*> -> {
                        val retrievedSat = stateAction.response as List<SatDomain>
                        emit(StateAction.SUCCESS(retrievedSat.filter { x ->
                            x.dbn == schoolDbn
                        }))
                    }
                }

            }
        }
    }
}
