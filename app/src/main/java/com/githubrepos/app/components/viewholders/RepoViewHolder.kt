package com.githubrepos.app.components.viewholders

import com.githubrepos.app.databinding.ViewHolderReposBinding
import com.githubrepos.domain.Repo

class RepoViewHolder(
    private val onRepoClicked: (Repo) -> Unit,
    binding: ViewHolderReposBinding
) : BaseViewHolder<Repo, ViewHolderReposBinding>(binding = binding) {

    override fun bind(item: Repo) {
        binding.root.setOnClickListener { onRepoClicked(item) }

        val breedName = item.name.split(" ")
            .joinToString(" ") { it.replaceFirstChar { firstChar -> firstChar.uppercase() } }
        binding.repoNameText.text = breedName
        binding.repoDescriptionText.text = item.description

    }

}