package com.dogbreeds.app.screens.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dogbreeds.app.R
import com.dogbreeds.app.databinding.FragmentBreedsBinding
import com.dogbreeds.app.screens.BaseFragment
import com.dogbreeds.domain.Breed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsFragment : BaseFragment<FragmentBreedsBinding, BreedsViewModel>() {

    override val viewModel: BreedsViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBreedsBinding = FragmentBreedsBinding.inflate(inflater, container, false)

    override fun initObservers() {
        super.initObservers()
        launchWhenStarted { viewModel.breedsState.collect(::observeBreeds) }
    }

    private fun observeBreeds(breeds: List<Breed>) {
        binding.composeView.setContent {
            LazyColumn(Modifier.fillMaxSize()) {
                items(breeds) { breed ->
                    BreedCard(breed, viewModel)
                }
            }
        }
        binding.emptyState.root.isVisible = breeds.isEmpty()
        binding.composeView.isVisible = breeds.isNotEmpty()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedCard(breed: Breed, viewModel: BreedsViewModel) {
    val breedName = breed.breedName.split(" ")
        .joinToString(" ") { it.replaceFirstChar { firstChar -> firstChar.uppercase() } }
    Card(
        onClick = { viewModel.onBreedClicked(breed) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.md_theme_light_primaryContainer))
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.ic_breed),
                null,
                Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .width(25.dp)
                    .height(25.dp)
            )
            Text(
                text = breedName,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}