package com.githubrepos.app.screens.breed_images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.githubrepos.app.R
import com.githubrepos.app.components.adapters.BreedImagesAdapter
import com.githubrepos.app.databinding.FragmentBreedImagesBinding
import com.githubrepos.app.screens.BaseFragment
import com.githubrepos.domain.BreedImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedImagesFragment : BaseFragment<FragmentBreedImagesBinding, BreedImagesViewModel>() {

    override val viewModel: BreedImagesViewModel by viewModels()

    private lateinit var breedImagesAdapter: BreedImagesAdapter

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBreedImagesBinding = FragmentBreedImagesBinding.inflate(inflater, container, false)

    override fun initViews() {
        super.initViews()
        initToolbar()
        initBreedImagesList()
    }

    override fun initObservers() {
        super.initObservers()
        launchWhenStarted { viewModel.breedImagesState.collect(::observeBreeds) }
        launchWhenStarted { viewModel.screenTitleState.collect(::observeScreenTitle) }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { viewModel.onToolbarNavigationClicked() }
    }

    private fun initBreedImagesList() {
        binding.breedImagesRecycler.setHasFixedSize(true)
        breedImagesAdapter = BreedImagesAdapter()
        binding.breedImagesRecycler.adapter = breedImagesAdapter
    }

    private fun observeBreeds(breedImages: List<BreedImage>) {
        breedImagesAdapter.submitList(breedImages)
        binding.emptyState.root.isVisible = breedImages.isEmpty()
        binding.breedImagesRecycler.isVisible = breedImages.isNotEmpty()
    }

    private fun observeScreenTitle(breedName: String?) {
        val upperCaseBreedName = breedName?.split(" ")
            ?.joinToString(" ") { it.replaceFirstChar { firstChar -> firstChar.uppercase() } }
        binding.toolbar.title =
            upperCaseBreedName ?: requireContext().getString(R.string.breed_images)
    }
}