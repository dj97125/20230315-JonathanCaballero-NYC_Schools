package com.example.nyc_schools_test.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.SatUseCase
import com.example.nyc_schools_test.domain.SchoolDomain
import com.example.nyc_schools_test.domain.SchoolUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class NYCViewModel @Inject constructor(
    private val satUseCase: SatUseCase,
    private val schoolUseCase: SchoolUseCase,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _schoolResponse = MutableLiveData<StateAction>()
    val schoolResponse: MutableLiveData<StateAction>
        get() = _schoolResponse

    private val _schoolSatResponse = MutableLiveData<StateAction>()
    val schoolSatResponse: MutableLiveData<StateAction>
        get() = _schoolSatResponse

    var school: SchoolDomain? = null

    init {
        getSchoolList()
    }

        fun getSchoolList() {
            val handler = CoroutineExceptionHandler { _, throwable ->
                _schoolResponse.postValue(StateAction.ERROR(throwable as Exception))
                Log.d("ViewModel", "$throwable")
            }
            coroutineScope.launch(handler) {
                supervisorScope {
                    launch {
                        schoolUseCase().collect {
                            _schoolResponse.postValue(it)
                        }
                    }
                }
            }
        }


        fun getSatList(schoolDbn: String) {
            val handler = CoroutineExceptionHandler { _, exception ->
                _schoolSatResponse.postValue(StateAction.ERROR(exception as Exception))
                Log.d("ViewModel", "$exception")
            }
            coroutineScope.launch(handler) {
                supervisorScope {
                    satUseCase(schoolDbn).collect {
                        _schoolSatResponse.postValue(it)
                    }
                }
            }
        }


}




