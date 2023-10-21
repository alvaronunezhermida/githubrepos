package com.githubrepos.app.screens.repo_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.githubrepos.app.databinding.FragmentRepoDetailBinding
import com.githubrepos.app.screens.BaseFragment
import com.githubrepos.domain.Repo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailFragment : BaseFragment<FragmentRepoDetailBinding, RepoDetailViewModel>() {

    override val viewModel: RepoDetailViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepoDetailBinding = FragmentRepoDetailBinding.inflate(inflater, container, false)

    override fun initViews() {
        super.initViews()
        initToolbar()
        binding.repoUrlButton.setOnClickListener { viewModel.onRepoUrlClicked() }
    }

    override fun initObservers() {
        super.initObservers()
        launchWhenStarted { viewModel.repoState.collect(::observeRepo) }
        launchWhenStarted { viewModel.countForksState.collect(::observeCountForks) }
        launchWhenStarted { viewModel.languageState.collect(::observeLanguage) }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { viewModel.onToolbarNavigationClicked() }
    }

    private fun observeRepo(repo: Repo?) {
        binding.repoTitle.text = repo?.name ?: ""
        binding.repoDescription.text = repo?.description ?: ""
        binding.starsCountText.text = repo?.stargazersCount.toString()
        binding.forksCountText.text = repo?.forksCount.toString()
        binding.languageCard.visibility = if (repo?.language.isNullOrBlank()) View.GONE else View.VISIBLE
        binding.languageText.text = repo?.language
    }

    private fun observeCountForks(count: Int) {
        binding.forksCountText.text = count.toString()
    }

    private fun observeLanguage(language: String) {
        binding.languageCard.visibility = if (language.isEmpty()) View.GONE else View.VISIBLE
        binding.languageText.text = language
    }

}