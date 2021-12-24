package com.kemsky.pokeapp.ui.allpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemsky.pokeapp.ViewModelFactory
import com.kemsky.pokeapp.databinding.FragmentAllPokemonBinding
import com.kemsky.pokeapp.ui.allpokemon.adapter.AllPokemonAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class AllPokemonFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: AllPokemonViewModel

    private var binding: FragmentAllPokemonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllPokemonBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory)[AllPokemonViewModel::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonAdapter = AllPokemonAdapter()
        binding?.rvAllPokemon?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvAllPokemon?.setHasFixedSize(true)

        binding?.rvAllPokemon?.adapter = pokemonAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listData.collectLatest { pagedData ->
                pokemonAdapter.submitData(pagedData)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}