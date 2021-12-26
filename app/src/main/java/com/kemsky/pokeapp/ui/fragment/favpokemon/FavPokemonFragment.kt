package com.kemsky.pokeapp.ui.fragment.favpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemsky.GetAllPokemonQuery
import com.kemsky.pokeapp.ViewModelFactory
import com.kemsky.pokeapp.databinding.FragmentFavPokemonBinding
import com.kemsky.pokeapp.ui.fragment.favpokemon.adapter.FavPokemonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FavPokemonFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: FavPokemonViewModel

    private var binding: FragmentFavPokemonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavPokemonBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory)[FavPokemonViewModel::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        configureRecyclerView()
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        binding?.toolbar?.title = "Favorite Pokemon"
    }

    private fun configureRecyclerView() {
        val pokemonAdapter = FavPokemonAdapter()
        binding?.rvAllPokemon?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvAllPokemon?.setHasFixedSize(true)

        binding?.rvAllPokemon?.adapter = pokemonAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding?.refresh?.setOnRefreshListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.fetchAllPokemon().collect()
                    if (binding?.refresh?.isRefreshing == true) {
                        binding?.refresh?.isRefreshing = false
                    }
                }
            }

            viewModel.fetchAllPokemon().collectLatest { all ->
                viewModel.getListFav().collect { fav ->
                    Timber.d(fav.toString())
                    if (fav.isNotEmpty()) {
                        val submitList = mutableListOf<GetAllPokemonQuery.Pokemon_v2_pokemon>()
                        fav.forEach { model ->
                            val submitedList = all.pokemon_v2_pokemon.filter { it.id == model.id }
                            submitList.addAll(submitedList)
                        }
                        pokemonAdapter.submitList(submitList)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}