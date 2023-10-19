package com.dogbreeds.app.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope

/**
 * An abstract base class for creating fragments with ViewBinding and ViewModel support.
 *
 * This class simplifies the creation of Android fragments by providing common functionality for handling ViewBinding, ViewModel integration, lifecycle events, and coroutines.
 *
 * @param VB The type of ViewBinding associated with the fragment.
 * @param VM The type of ViewModel associated with the fragment.
 */
abstract class BaseFragment<out VB : ViewBinding, out VM : BaseViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = initBinding(inflater = inflater, container = container)
        return binding.root
    }

    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.run { initInstance(savedInstanceState = this) }
        initViews()
        initObservers()
    }

    protected open fun initInstance(savedInstanceState: Bundle) = Unit

    protected open fun initViews() = Unit

    protected open fun initObservers() {

    }

    protected fun launchWhenCreated(body: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenCreated(body)
    }

    protected fun launchWhenStarted(body: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenStarted(body)
    }

    protected fun launchWhenResumed(body: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenResumed(body)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStarted()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    open fun onBackPressed() = true

    open fun onKeyboardVisibleChanged(isVisible: Boolean) = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}