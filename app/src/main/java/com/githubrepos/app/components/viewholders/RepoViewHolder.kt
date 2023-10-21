package com.githubrepos.app.components.viewholders

import com.githubrepos.app.databinding.ViewHolderReposBinding
import com.githubrepos.domain.Repo

class RepoViewHolder(
    private val onRepoClicked: (Repo) -> Unit,
    binding: ViewHolderReposBinding
) : BaseViewHolder<Repo, ViewHolderReposBinding>(binding = binding) {

    override fun bind(item: Repo) {
        binding.root.setOnClickListener { onRepoClicked(item) }
        binding.repoNameText.text = item.name
        binding.repoDescriptionText.text = item.description
        binding.stargazersCountText.text = item.stargazersCount.toString()

    }

}