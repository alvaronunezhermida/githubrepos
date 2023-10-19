package com.githubrepos.app.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope

abstract class BaseDialog<out VB : ViewBinding, out VM : BaseViewModel> : DialogFragment() {

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
    }

    protected open fun initInstance(savedInstanceState: Bundle) = Unit

    protected open fun initViews() = Unit

    protected fun launchWhenCreated(body: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenCreated(body)
    }

    protected fun launchWhenStarted(body: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenStarted(body)
    }

    protected fun launchWhenResumed(body: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenResumed(body)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}