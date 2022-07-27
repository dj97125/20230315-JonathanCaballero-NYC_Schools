package com.example.nyc_schools_test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nyc_schools_test.common.FailedResponseException
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.SatDomain
import com.example.nyc_schools_test.domain.SatUseCase
import com.example.nyc_schools_test.domain.SchoolDomain
import com.example.nyc_schools_test.domain.SchoolUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals


@ExperimentalCoroutinesApi
class NYCViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()
    lateinit var subject: NYCViewModel
    lateinit var schoolUseCase: SchoolUseCase
    lateinit var satUseCase: SatUseCase
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScopeCoroutine = TestScope(testDispatcher)

    @Before
    fun setUpTest() {
        schoolUseCase = mockk()
        satUseCase = mockk()
        subject = NYCViewModel(satUseCase, schoolUseCase, testScopeCoroutine)
    }

    @Test
    fun `test everything works`() = runTest(testDispatcher) {
        testDispatcher.scheduler.advanceTimeBy(2000L)
        Assert.assertTrue(true)
    }

    @Test
    fun `get SCHOOL list when fetching data from server returns SUCCESS response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            val listSchool = listOf(mockk<SchoolDomain>(relaxed = true))

            every {
                schoolUseCase.invoke()
            } returns flowOf(
                StateAction.SUCCESS(
                    listOf(listSchool)
                )
            )
            /**
             * When
             */
            subject.getSchoolList()
            var StateActionTestList = mutableListOf<StateAction>()
            val handler = CoroutineExceptionHandler { _, throwable ->
            }
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.schoolResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val successTest =
                StateActionTestList.get(0) as StateAction.SUCCESS<List<SchoolDomain>>
            assertEquals(successTest.response.size, StateActionTestList.size)

            job.cancel()

        }

    @Test
    fun `get SCHOOL list when fetching data from server returns ERROR response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            every {
                schoolUseCase.invoke()
            } returns flowOf(
                StateAction.ERROR(FailedResponseException())
            )
            /**
             * When
             */
            subject.getSchoolList()
            var StateActionTestList = mutableListOf<StateAction>()
            val handler = CoroutineExceptionHandler { _, throwable ->
            }
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.schoolResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val errorTest =
                StateActionTestList.get(0) as StateAction.ERROR
            assertThat(errorTest).isInstanceOf(StateAction.ERROR::class.java)


            job.cancel()

        }

    @Test
    fun `get SAT list when fetching data from server returns SUCCESS response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            val listSat = listOf(mockk<SatDomain>(relaxed = true))

            every {
                satUseCase.invoke("07X334")
            } returns flowOf(
                StateAction.SUCCESS(
                    listOf(listSat)
                )
            )
            /**
             * When
             */
            subject.getSatList("07X334")
            var StateActionTestList = mutableListOf<StateAction>()
            val handler = CoroutineExceptionHandler { _, throwable ->
            }
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.schoolSatResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val successTest =
                StateActionTestList.get(0) as StateAction.SUCCESS<List<SatDomain>>
            assertEquals(successTest.response.size, StateActionTestList.size)

            job.cancel()

        }


    @Test
    fun `get SAT list when fetching data from server returns ERROR response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            every {
                satUseCase.invoke("07X334")
            } returns flowOf(
                StateAction.ERROR(FailedResponseException())
            )
            /**
             * When
             */
            subject.getSatList("07X334")
            var StateActionTestList = mutableListOf<StateAction>()
            val handler = CoroutineExceptionHandler { _, throwable ->
            }
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.schoolSatResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val errorTest =
                StateActionTestList.get(0) as StateAction.ERROR
            assertThat(errorTest).isInstanceOf(StateAction.ERROR::class.java)

            job.cancel()

        }


    @After
    fun shutdownTest() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

}