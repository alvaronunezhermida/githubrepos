package com.githubrepos.app.screens.breeds

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
class BreedsViewModel @Inject constructor(
    private val getAllReposUseCase: GetAllReposUseCase,
    private val loadAllReposUseCase: LoadAllReposUseCase,
    appNavigator: AppNavigator
) : AppViewModel(appNavigator = appNavigator) {

    private val breedsMutableState = MutableStateFlow(emptyList<Repo>())
    val breedsState: StateFlow<List<Repo>>
        get() = breedsMutableState

    override fun onStarted() {
        super.onStarted()
        launchGetBreeds()
        launchLoadAllBreeds()
    }

    private fun launchGetBreeds() {
        launch {
            getAllReposUseCase().collect { breeds ->
                breedsMutableState.value = breeds
            }
        }
    }

    private fun launchLoadAllBreeds() {
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


    fun onBreedClicked(repo: Repo) {
        appNavigator.fromBreedsToBreedImages(repo.breedName)
    }

}