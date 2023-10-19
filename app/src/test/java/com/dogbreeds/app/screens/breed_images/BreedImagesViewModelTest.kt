package com.dogbreeds.app.screens.breed_images

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.right
import com.dogbreeds.app.navigation.AppNavigator
import com.dogbreeds.app.testcommons.CoroutinesTestRule
import com.dogbreeds.app.testcommons.sampleBreedImage
import com.dogbreeds.usecases.GetBreedImagesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class BreedImagesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getBreedImagesUseCase: GetBreedImagesUseCase

    @Mock
    lateinit var appNavigator: AppNavigator

    private val state: SavedStateHandle = SavedStateHandle()

    private lateinit var vm: BreedImagesViewModel

    private val breedImages = listOf(sampleBreedImage)

    private val breedImagesParams = GetBreedImagesUseCase.Params("affenpinscher", 10)

    private val breedName = "affenpinscher"

    @Before
    fun setUp() {
        state["breedName"] = breedName
        whenever(getBreedImagesUseCase(breedImagesParams)).thenReturn(flowOf(breedImages.right()))
        vm = BreedImagesViewModel(getBreedImagesUseCase, state, appNavigator)
        vm.onStarted()
    }

    @Test
    fun `BreedImages are loaded when viewmodel starts`() = runTest {
        verify(getBreedImagesUseCase).invoke(breedImagesParams)
    }

    @Test
    fun `Images State is updated when data is retrieved`() = runTest {
        vm.breedImagesState.test {
            assertEquals(breedImages, awaitItem())
        }
    }

    @Test
    fun `Title State is updated when viewmodel starts`() = runTest {
        vm.screenTitleState.test {
            assertEquals(breedName, awaitItem())
        }
    }

    @Test
    fun `goBack() is called when user clicks in the back toolbar arrow`() = runTest {
        vm.onToolbarNavigationClicked()
        verify(appNavigator).goBack()
    }
}
