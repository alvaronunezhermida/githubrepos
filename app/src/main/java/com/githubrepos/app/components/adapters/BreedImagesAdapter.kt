package com.githubrepos.app.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.githubrepos.app.components.diffs.BreedImagesDiff
import com.githubrepos.app.components.viewholders.BreedImageViewHolder
import com.githubrepos.app.databinding.ViewHolderBreedImageBinding

class BreedImagesAdapter() : ListAdapter<BreedImage, BreedImageViewHolder>(BreedImagesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedImageViewHolder =
        BreedImageViewHolder(
            binding = ViewHolderBreedImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: BreedImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}