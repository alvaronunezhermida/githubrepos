package com.githubrepos.app.components.viewholders

import coil.load
import com.githubrepos.app.databinding.ViewHolderBreedImageBinding

class BreedImageViewHolder(
    binding: ViewHolderBreedImageBinding
) : BaseViewHolder<BreedImage, ViewHolderBreedImageBinding>(binding = binding) {

    override fun bind(item: BreedImage) {
        binding.breedImage.load(item.imageUrl)
    }

}