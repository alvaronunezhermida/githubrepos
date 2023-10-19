package com.dogbreeds.app.screens.error

import com.dogbreeds.app.navigation.AppNavigator
import com.dogbreeds.app.testcommons.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ErrorViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var appNavigator: AppNavigator

    private lateinit var vm: ErrorViewModel

    @Before
    fun setUp() {
        vm = ErrorViewModel(appNavigator)
    }

    @Test
    fun `Navigates back when okButtonClicked is called`() = runTest {
        vm.onOkButtonClicked()
        verify(appNavigator).goBack()
    }
}
