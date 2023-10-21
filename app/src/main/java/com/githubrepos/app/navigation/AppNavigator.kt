package com.githubrepos.app.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.githubrepos.app.R
import com.githubrepos.app.screens.repo_detail.RepoDetailConfig.ARG_KEY
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

    fun fromReposToRepoDetail(repoId: Int) {
        goTo(id = R.id.action_reposFragment_to_repoDetailFragment,
            args = Bundle().apply {
                putInt(ARG_KEY, repoId)
            })
    }

    fun openUrl(url: String) {
        goTo {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        }
    }
}