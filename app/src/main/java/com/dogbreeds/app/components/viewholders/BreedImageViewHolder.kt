package com.dogbreeds.app.components.viewholders

import coil.load
import com.dogbreeds.app.databinding.ViewHolderBreedImageBinding
import com.dogbreeds.domain.BreedImage

class BreedImageViewHolder(
    binding: ViewHolderBreedImageBinding
) : BaseViewHolder<BreedImage, ViewHolderBreedImageBinding>(binding = binding) {

    override fun bind(item: BreedImage) {
        binding.breedImage.load(item.imageUrl)
    }

}