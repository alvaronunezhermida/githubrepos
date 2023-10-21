package com.githubrepos.app.screens.repo_detail

import androidx.lifecycle.SavedStateHandle
import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.screens.AppViewModel
import com.githubrepos.app.screens.repo_detail.RepoDetailConfig.ARG_KEY
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import com.githubrepos.usecases.CountForksUseCase
import com.githubrepos.usecases.GetLanguageUseCase
import com.githubrepos.usecases.GetRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val getRepoUseCase: GetRepoUseCase,
    private val countForksUseCase: CountForksUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val state: SavedStateHandle,
    appNavigator: AppNavigator
) : AppViewModel(appNavigator = appNavigator) {

    private val repoMutableState = MutableStateFlow<Repo?>(null)
    val repoState: StateFlow<Repo?>
        get() = repoMutableState

    private val countForksMutableState = MutableStateFlow(0)
    val countForksState: StateFlow<Int>
        get() = countForksMutableState

    private val languageMutableState = MutableStateFlow("")
    val languageState: StateFlow<String>
        get() = languageMutableState

    override fun onStarted() {
        super.onStarted()
        launchGetRepo()
    }

    private fun launchGetRepo() {
        state.get<Int>(ARG_KEY)?.let {
            launch {
                getRepoUseCase(GetRepoUseCase.Params(it)).collect { either ->
                    either.fold(
                        ifLeft = { error ->
                            handleError(error)
                        },
                        ifRight = { repo ->
                            repoMutableState.value = repo
                            if (repo.forksCount == 0) loadCountForks(repo)
                            if (repo.language.isNullOrBlank()) loadGetLanguage(repo)
                        }
                    )
                }
            }
        } ?: handleError(Error.NullParams)
    }

    private fun loadCountForks(repo: Repo){
        repo.forksUrl?.let {
            launch {
                countForksUseCase(CountForksUseCase.Params(repo.id, it)).collect { either ->
                    either.fold(
                        ifLeft = { error ->
                            handleError(error)
                        },
                        ifRight = { count ->
                            countForksMutableState.value = count
                        }
                    )
                }
            }
        }
    }

    private fun loadGetLanguage(repo: Repo){
        repo.languagesUrl?.let {
            launch {
                getLanguageUseCase(GetLanguageUseCase.Params(repo.id, it)).collect { either ->
                    either.fold(
                        ifLeft = { error ->
                            handleError(error)
                        },
                        ifRight = { language ->
                            languageMutableState.value = language
                        }
                    )
                }
            }
        }
    }

    fun onToolbarNavigationClicked() {
        appNavigator.goBack()
    }

    fun onRepoUrlClicked() {
        repoState.value?.htmlUrl?.let {
            appNavigator.openUrl(it)
        }
    }

}