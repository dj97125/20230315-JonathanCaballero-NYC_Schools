package com.example.nyc_schools_test.model.local

import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.SatDomain
import com.example.nyc_schools_test.domain.SchoolDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalDataSource {
    suspend fun insertLocalSChool(schoolListEntity: List<SchoolDomain>): Flow<StateAction>
    suspend fun insertLocalSat(schoolSatEntity: List<SatDomain>): Flow<StateAction>
    suspend fun deleteAllSchoolLocalItem()
    suspend fun deleteAllSatLocalItem()
    suspend fun getAllCachedSchools(): Flow<StateAction>
    suspend fun getAllCachedSat(): Flow<StateAction>
}

class LocalDataSourceImpl @Inject constructor(
    private val schoolDao: SchoolDao
) : LocalDataSource {
    override suspend fun insertLocalSChool(schoolListEntity: List<SchoolDomain>): Flow<StateAction> =
        flow {
            schoolDao.insertLocalSChool(schoolListEntity.fromDomainSchoolModel())
        }

    override suspend fun insertLocalSat(schoolSatEntity: List<SatDomain>): Flow<StateAction> =
        flow {
            schoolDao.insertLocalSat(schoolSatEntity.fromDomainSatModel())
        }

    override suspend fun deleteAllSchoolLocalItem() {
        return schoolDao.deleteAllSchoolLocalItem()
    }

    override suspend fun deleteAllSatLocalItem() {
        return schoolDao.deleteAllSatLocalItem()
    }

    override suspend fun getAllCachedSchools(): Flow<StateAction> = flow {
        val cache = schoolDao.getAllCachedSchools()
        cache.let {
            emit(StateAction.SUCCESS(cache.map { it.toDomainSchoolModel() }))
        }
    }

    override suspend fun getAllCachedSat(): Flow<StateAction> = flow {
        val cache = schoolDao.getAllCachedSat()
        cache.let {
            emit(StateAction.SUCCESS(cache.map { it.toDomainSatModel() }))
        }
    }
}

private fun List<SchoolListEntity>.toDomainSchoolModel(): List<SchoolDomain> = map {
    it.toDomainSchoolModel()
}
private fun List<SchoolSatEntity>.toDomainSatModel(): List<SatDomain> = map {
    it.toDomainSatModel()
}

private fun List<SchoolDomain>.fromDomainSchoolModel(): List<SchoolListEntity> = map {
    it.fromDomainSchoolModel()
}
private fun List<SatDomain>.fromDomainSatModel(): List<SchoolSatEntity> = map {
    it.fromDomainSatModel()
}

private fun SchoolListEntity.toDomainSchoolModel(): SchoolDomain = SchoolDomain(
    dbn,
    school_name,
    overview_paragraph,
    neighborhood,
    location,
    phone_number,
    school_email,
    website
)

private fun SchoolSatEntity.toDomainSatModel(): SatDomain = SatDomain(
    id, dbn, satTestTakers, readingAvg, mathAvg, writingAvg
)

private fun SchoolDomain.fromDomainSchoolModel(): SchoolListEntity = SchoolListEntity(
    id = 0,
    dbn,
    school_name,
    overview_paragraph,
    neighborhood,
    location,
    phone_number,
    school_email,
    website
)

private fun SatDomain.fromDomainSatModel(): SchoolSatEntity = SchoolSatEntity(
    id = 0, dbn, satTestTakers, readingAvg, mathAvg, writingAvg
)