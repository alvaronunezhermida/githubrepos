package com.githubrepos.app.screens.breed_images

import androidx.lifecycle.SavedStateHandle
import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.screens.AppViewModel
import com.githubrepos.app.screens.breed_images.BreedImagesConfig.ARG_KEY
import com.githubrepos.domain.BreedImage
import com.githubrepos.domain.Error
import com.githubrepos.usecases.GetBreedImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BreedImagesViewModel @Inject constructor(
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
    private val state: SavedStateHandle,
    appNavigator: AppNavigator
) : AppViewModel(appNavigator = appNavigator) {

    private val breedImagesMutableState = MutableStateFlow(emptyList<BreedImage>())
    val breedImagesState: StateFlow<List<BreedImage>>
        get() = breedImagesMutableState

    private val screenTitleMutableState = MutableStateFlow<String?>(null)
    val screenTitleState: StateFlow<String?>
        get() = screenTitleMutableState

    override fun onStarted() {
        super.onStarted()
        launchGetBreedImages()
    }

    private fun launchGetBreedImages() {
        state.get<String>(ARG_KEY)?.let { breedName ->
            screenTitleMutableState.value = breedName
            launch {
                getBreedImagesUseCase(
                    GetBreedImagesUseCase.Params(
                        breedName,
                        10
                    )
                ).collect { either ->
                    either.fold(
                        ifLeft = { error ->
                            appNavigator.toError(error)
                        },
                        ifRight = { breedImages ->
                            breedImagesMutableState.value = breedImages
                        }
                    )

                }
            }
        } ?: appNavigator.toError(Error.NullParams)
    }

    fun onToolbarNavigationClicked() {
        appNavigator.goBack()
    }

}