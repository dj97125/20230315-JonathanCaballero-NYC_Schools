package com.example.nyc_schools_test.domain

import com.example.nyc_schools_test.model.remote.Repository
import javax.inject.Inject

class SchoolUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.NYCSchoolCatched()
}