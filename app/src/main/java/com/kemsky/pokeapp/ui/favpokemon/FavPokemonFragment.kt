package com.kemsky.pokeapp.ui.favpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kemsky.pokeapp.R
import com.kemsky.pokeapp.databinding.FragmentFavPokemonBinding

class FavPokemonFragment : Fragment() {

    private lateinit var binding: FragmentFavPokemonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav_pokemon, container, false)
    }

}