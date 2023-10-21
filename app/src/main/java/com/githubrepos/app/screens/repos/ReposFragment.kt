package com.githubrepos.app.screens.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.githubrepos.app.components.adapters.ReposAdapter
import com.githubrepos.app.databinding.FragmentReposBinding
import com.githubrepos.app.screens.BaseFragment
import com.githubrepos.domain.Repo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsFragment : BaseFragment<FragmentReposBinding, ReposViewModel>() {

    override val viewModel: ReposViewModel by viewModels()

    private lateinit var reposAdapter: ReposAdapter

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReposBinding = FragmentReposBinding.inflate(inflater, container, false)

    override fun initViews() {
        super.initViews()
        initReposList()
    }

    override fun initObservers() {
        super.initObservers()
        launchWhenStarted { viewModel.reposState.collect(::observeBreeds) }
    }

    private fun initReposList() {
        binding.reposRecycler.setHasFixedSize(true)
        reposAdapter =
            ReposAdapter(onRepoClicked = viewModel::onRepoClicked)
        binding.reposRecycler.adapter = reposAdapter
    }

    private fun observeBreeds(repos: List<Repo>) {
        reposAdapter.submitList(repos)
        binding.emptyState.root.isVisible = repos.isEmpty()
        binding.reposRecycler.isVisible = repos.isNotEmpty()
    }

}