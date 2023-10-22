package com.githubrepos.app.screens.repo_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.githubrepos.app.databinding.FragmentRepoDetailBinding
import com.githubrepos.app.screens.BaseFragment
import com.githubrepos.domain.Repo
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

        binding.forksLoading.visibility = if (repo?.forksCount == null) View.VISIBLE else View.GONE
        binding.forksCountText.visibility = if (repo?.forksCount == null) View.GONE else View.VISIBLE
        binding.forksCountText.text = (repo?.forksCount?:0).toString()

        binding.languageLoading.visibility = if (repo?.language.isNullOrEmpty()) View.VISIBLE else View.GONE
        binding.languageText.visibility = if (repo?.language.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.languageText.text = repo?.language
    }

    private fun observeCountForks(count: Int?) {
        binding.forksLoading.visibility = if (count == null) View.VISIBLE else View.GONE
        binding.forksCountText.visibility = if (count == null) View.GONE else View.VISIBLE
        binding.forksCountText.text = (count?:0).toString()
    }

    private fun observeLanguage(language: String?) {
        binding.languageLoading.visibility = if (language.isNullOrEmpty()) View.VISIBLE else View.GONE
        binding.languageText.visibility = if (language.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.languageText.text = language
    }

}