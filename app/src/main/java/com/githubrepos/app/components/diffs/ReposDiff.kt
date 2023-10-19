package com.githubrepos.app.components.diffs

import androidx.recyclerview.widget.DiffUtil
import com.githubrepos.domain.Repo

class ReposDiff : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem.id == newItem.id

}