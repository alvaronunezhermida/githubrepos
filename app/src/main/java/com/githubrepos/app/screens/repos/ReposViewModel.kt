package com.githubrepos.app.screens.repos

import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.screens.AppViewModel
import com.githubrepos.domain.Repo
import com.githubrepos.usecases.CountStargazersUseCase
import com.githubrepos.usecases.GetAllReposUseCase
import com.githubrepos.usecases.LoadAllReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val getAllReposUseCase: GetAllReposUseCase,
    private val loadAllReposUseCase: LoadAllReposUseCase,
    private val countStargazersUseCase: CountStargazersUseCase,
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
            fullscreenLoaderMutableState.value = true
            getAllReposUseCase().collect { repos ->
                reposMutableState.value = repos
                if (repos.isNotEmpty()) fullscreenLoaderMutableState.value = false
            }
        }
    }

    private fun launchLoadAllRepos() {
        launch {
            fullscreenLoaderMutableState.value = true
            loadAllReposUseCase().collect { either ->
                either.fold(
                    ifLeft = { error ->
                        appNavigator.toError(error)
                    },
                    ifRight = { repos ->
                        repos.map { repoItem ->
                            async {
                                repoItem.stargazersUrl?.let { stargazersUrl ->
                                    countStargazersUseCase(
                                        CountStargazersUseCase.Params(
                                            repoItem.id,
                                            stargazersUrl
                                        )
                                    ).collect { response ->
                                        response.fold(
                                            ifLeft = { error ->
                                                appNavigator.toError(error)
                                            },
                                            ifRight = { _ ->
                                                //do nothing
                                            }
                                        )
                                    }
                                }
                            }
                            fullscreenLoaderMutableState.value = false
                        }
                    }
                )

            }
        }
    }


    fun onRepoClicked(repo: Repo) {
        appNavigator.fromReposToRepoDetail(repo.id)
    }

}