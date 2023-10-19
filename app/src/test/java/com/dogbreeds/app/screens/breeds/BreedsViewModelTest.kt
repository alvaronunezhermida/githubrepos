package com.dogbreeds.app.screens.breeds

import app.cash.turbine.test
import arrow.core.right
import com.dogbreeds.app.navigation.AppNavigator
import com.dogbreeds.app.testcommons.CoroutinesTestRule
import com.dogbreeds.app.testcommons.sampleBreed
import com.dogbreeds.domain.Empty
import com.dogbreeds.usecases.GetAllBreedsUseCase
import com.dogbreeds.usecases.LoadAllBreedsUseCase
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
class BreedsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getAllBreedsUseCase: GetAllBreedsUseCase

    @Mock
    lateinit var loadAllBreedsUseCase: LoadAllBreedsUseCase

    @Mock
    lateinit var appNavigator: AppNavigator

    private lateinit var vm: BreedsViewModel

    private val breeds = listOf(sampleBreed)

    @Before
    fun setUp() {
        whenever(getAllBreedsUseCase()).thenReturn(flowOf(breeds))
        whenever(loadAllBreedsUseCase()).thenReturn(flowOf(Empty().right()))
        vm = BreedsViewModel(getAllBreedsUseCase, loadAllBreedsUseCase, appNavigator)
        vm.onStarted()
    }

    @Test
    fun `Breeds are loaded from cache when viewmodel starts`() = runTest {
        runCurrent()
        verify(getAllBreedsUseCase).invoke()
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.breedsState.test {
            assertEquals(breeds, awaitItem())
        }
    }

    @Test
    fun `Breeds are requested when viewmodel starts`() = runTest {
        runCurrent()
        verify(loadAllBreedsUseCase).invoke()
    }
}
