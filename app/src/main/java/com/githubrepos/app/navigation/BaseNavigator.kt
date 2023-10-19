package com.githubrepos.app.navigation

import android.content.Context
import android.content.Intent

abstract class BaseNavigator {

    protected lateinit var context: Context

    protected fun goTo(getIntent: (context: Context) -> Intent) {
        context.startActivity(getIntent(context))
    }


}