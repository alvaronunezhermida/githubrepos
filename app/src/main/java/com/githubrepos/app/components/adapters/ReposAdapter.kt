package com.githubrepos.app.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.githubrepos.app.components.diffs.ReposDiff
import com.githubrepos.app.components.viewholders.RepoViewHolder
import com.githubrepos.app.databinding.ViewHolderReposBinding
import com.githubrepos.domain.Repo

class ReposAdapter(
    private val onRepoClicked: (Repo) -> Unit,
) : ListAdapter<Repo, RepoViewHolder>(ReposDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            onRepoClicked = onRepoClicked,
            binding = ViewHolderReposBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}