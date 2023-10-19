package com.githubrepos.app.navigation

import android.os.Bundle
import com.githubrepos.app.R
import com.githubrepos.app.screens.breed_images.BreedImagesConfig.ARG_KEY
import javax.inject.Inject

/**
 * A navigation class that extends [ActivityNavigator] and provides navigation functions specific to the app's flow.
 *
 * This class defines navigation actions that allow transitioning between different fragments and screens within the application.
 *
 * @constructor Creates an [AppNavigator] instance using Dagger Hilt injection.
 * @see ActivityNavigator
 */
class AppNavigator @Inject constructor() : ActivityNavigator() {

    fun fromBreedsToBreedImages(breedName: String) {
        goTo(id = R.id.action_breedsFragment_to_breedImagesFragment,
            args = Bundle().apply {
                putString(ARG_KEY, breedName)
            })
    }
}