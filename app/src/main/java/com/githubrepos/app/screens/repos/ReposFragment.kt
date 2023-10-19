package com.githubrepos.app.screens.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.githubrepos.app.databinding.FragmentReposBinding
import com.githubrepos.app.screens.BaseFragment
import com.githubrepos.domain.Repo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsFragment : BaseFragment<FragmentReposBinding, ReposViewModel>() {

    override val viewModel: ReposViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReposBinding = FragmentReposBinding.inflate(inflater, container, false)

    override fun initObservers() {
        super.initObservers()
        launchWhenStarted { viewModel.reposState.collect(::observeBreeds) }
    }

    private fun observeBreeds(repos: List<Repo>) {
        breedsAdapter.submitList(repos)
        binding.emptyState.root.isVisible = repos.isEmpty()
        binding.reposRecycler.isVisible = repos.isNotEmpty()
    }

}