package com.githubrepos.app.components.diffs

import androidx.recyclerview.widget.DiffUtil
import com.githubrepos.domain.BreedImage

class BreedImagesDiff : DiffUtil.ItemCallback<BreedImage>() {

    override fun areItemsTheSame(oldItem: BreedImage, newItem: BreedImage): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: BreedImage, newItem: BreedImage): Boolean =
        oldItem.imageUrl == newItem.imageUrl

}