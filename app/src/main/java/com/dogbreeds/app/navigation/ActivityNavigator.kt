package com.dogbreeds.app.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dogbreeds.app.R
import com.dogbreeds.app.mappers.toEntry
import com.dogbreeds.app.screens.error.ErrorArgs
import com.dogbreeds.domain.Error
import javax.inject.Inject

/**
 * An abstract base class for implementing navigation within Android activities.
 *
 * This class serves as a foundation for handling navigation within activities by providing common navigation functionality.
 *
 * @see BaseNavigator
 * @property activity The [AppCompatActivity] associated with this navigator.
 */
abstract class ActivityNavigator : BaseNavigator() {

    @Inject
    lateinit var activity: AppCompatActivity

    private val navController: NavController? get() = activity.supportFragmentManager.primaryNavigationFragment?.findNavController()

    fun init(activity: AppCompatActivity) {
        context = activity
        this.activity = activity
    }


    protected fun goTo(id: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
        navController?.navigate(id, args, navOptions)
    }

    fun toError(error: Error) {
        goTo(
            id = R.id.action_to_errorFragment,
            args = Bundle().apply {
                putParcelable(ErrorArgs.ENTRY, error.toEntry())
            }
        )
    }

    fun goBack() {
        if (navController?.popBackStack() != true) {
            activity.finish()
        }
    }

}