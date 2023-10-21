package com.githubrepos.app.screens.repos

import app.cash.turbine.test
import arrow.core.right
import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.testcommons.CoroutinesTestRule
import com.githubrepos.app.testcommons.sampleRepo
import com.githubrepos.domain.Empty
import com.githubrepos.usecases.GetAllReposUseCase
import com.githubrepos.usecases.LoadAllReposUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ReposViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getAllReposUseCase: GetAllReposUseCase

    @Mock
    lateinit var loadAllReposUseCase: LoadAllReposUseCase

    @Mock
    lateinit var appNavigator: AppNavigator

    private lateinit var vm: ReposViewModel

    private val breeds = listOf(sampleRepo)

    @Before
    fun setUp() {
        whenever(getAllReposUseCase()).thenReturn(flowOf(breeds))
        whenever(loadAllReposUseCase()).thenReturn(flowOf(Empty().right()))
        vm = ReposViewModel(getAllReposUseCase, loadAllReposUseCase, appNavigator)
        vm.onStarted()
    }

    @Test
    fun `Breeds are loaded from cache when viewmodel starts`() = runTest {
        runCurrent()
        verify(getAllReposUseCase).invoke()
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.reposState.test {
            assertEquals(breeds, awaitItem())
        }
    }

    @Test
    fun `Breeds are requested when viewmodel starts`() = runTest {
        runCurrent()
        verify(loadAllReposUseCase).invoke()
    }
}
