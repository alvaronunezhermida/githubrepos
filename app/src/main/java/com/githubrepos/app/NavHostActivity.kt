package com.githubrepos.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.githubrepos.app.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
        initNavigator()
    }

    private fun initNavigator() {
        appNavigator.init(activity = this)
    }
}