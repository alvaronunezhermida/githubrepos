package com.githubrepos.app.screens.repo_detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.right
import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.testcommons.CoroutinesTestRule
import com.githubrepos.app.testcommons.sampleRepo
import com.githubrepos.usecases.CountForksUseCase
import com.githubrepos.usecases.GetLanguageUseCase
import com.githubrepos.usecases.GetRepoUseCase
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
class RepoDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getRepoUseCase: GetRepoUseCase

    @Mock
    lateinit var countForksUseCase: CountForksUseCase

    @Mock
    lateinit var getLanguageUseCase: GetLanguageUseCase

    @Mock
    lateinit var appNavigator: AppNavigator

    private val state: SavedStateHandle = SavedStateHandle()

    private lateinit var vm: RepoDetailViewModel

    private val repo = sampleRepo

    private val repoParams = GetRepoUseCase.Params(1)

    private val repoId = 1

    @Before
    fun setUp() {
        state["repoId"] = repoId
        whenever(getRepoUseCase(repoParams)).thenReturn(flowOf(repo.right()))
        whenever(countForksUseCase()).thenReturn(flowOf((repo.forksCount ?: 0).right()))
        vm = RepoDetailViewModel(
            getRepoUseCase,
            countForksUseCase,
            getLanguageUseCase,
            state,
            appNavigator
        )
        vm.onStarted()
    }

    @Test
    fun `Repo is retrieved when viewmodel starts`() = runTest {
        verify(getRepoUseCase).invoke(repoParams)
    }

    @Test
    fun `Repo State is updated when data is retrieved`() = runTest {
        vm.repoState.test {
            assertEquals(repo, awaitItem())
        }
    }

    @Test
    fun `goBack() is called when user clicks in the back toolbar arrow`() = runTest {
        vm.onToolbarNavigationClicked()
        verify(appNavigator).goBack()
    }
}
