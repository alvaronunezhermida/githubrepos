package com.githubrepos.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.githubrepos.app.databinding.ActivityNavHostBinding
import com.githubrepos.app.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {

    enum class State {
        IDLE,
        LOADING
    }


    private lateinit var binding: ActivityNavHostBinding

    private var state = State.IDLE

    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigator()
    }

    private fun initNavigator() {
        appNavigator.init(activity = this)
    }

    fun showFullscreenLoader() {
        state = State.LOADING
        binding.fullscreenLoader.fullscreenLoaderLayout.isVisible = true
    }

    fun hideFullscreenLoader() {
        state = State.IDLE
        binding.fullscreenLoader.fullscreenLoaderLayout.isVisible = false
    }
}