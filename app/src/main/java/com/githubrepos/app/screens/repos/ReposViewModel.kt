package com.githubrepos.app.screens.repos

import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.screens.AppViewModel
import com.githubrepos.domain.Repo
import com.githubrepos.usecases.GetAllReposUseCase
import com.githubrepos.usecases.LoadAllReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val getAllReposUseCase: GetAllReposUseCase,
    private val loadAllReposUseCase: LoadAllReposUseCase,
    appNavigator: AppNavigator
) : AppViewModel(appNavigator = appNavigator) {

    private val reposMutableState = MutableStateFlow(emptyList<Repo>())
    val reposState: StateFlow<List<Repo>>
        get() = reposMutableState

    override fun onStarted() {
        super.onStarted()
        launchGetRepos()
        launchLoadAllRepos()
    }

    private fun launchGetRepos() {
        launch {
            getAllReposUseCase().collect { repos ->
                reposMutableState.value = repos
            }
        }
    }

    private fun launchLoadAllRepos() {
        launch {
            loadAllReposUseCase().collect { either ->
                either.fold(
                    ifLeft = { error ->
                        appNavigator.toError(error)
                    },
                    ifRight = { _ ->
                        // Do nothing
                    }
                )

            }
        }
    }


    fun onRepoClicked(repo: Repo) {
        //TODO: implement navigation no detail
    }

}