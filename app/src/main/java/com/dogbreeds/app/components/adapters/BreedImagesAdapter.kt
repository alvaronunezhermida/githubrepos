package com.dogbreeds.app.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dogbreeds.app.components.diffs.BreedImagesDiff
import com.dogbreeds.app.components.viewholders.BreedImageViewHolder
import com.dogbreeds.app.databinding.ViewHolderBreedImageBinding
import com.dogbreeds.domain.BreedImage

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