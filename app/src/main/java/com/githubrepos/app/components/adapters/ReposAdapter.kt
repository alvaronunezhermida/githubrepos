package com.githubrepos.app.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dogbreeds.app.components.diffs.BreedsDiff
import com.dogbreeds.app.components.viewholders.BreedViewHolder
import com.dogbreeds.app.databinding.ViewHolderBreedsBinding
import com.dogbreeds.domain.Breed

class ReposAdapter(
    private val onBreedClicked: (Breed) -> Unit,
) : ListAdapter<Breed, BreedViewHolder>(BreedsDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder =
        BreedViewHolder(
            onBreedClicked = onBreedClicked,
            binding = ViewHolderBreedsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}