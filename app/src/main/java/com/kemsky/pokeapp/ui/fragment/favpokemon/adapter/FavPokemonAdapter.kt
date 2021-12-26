package com.kemsky.pokeapp.ui.fragment.favpokemon.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kemsky.GetAllPokemonQuery
import com.kemsky.pokeapp.R
import com.kemsky.pokeapp.constant.AppConstant.getImageUrl
import com.kemsky.pokeapp.databinding.ItemPokemonBinding
import com.kemsky.pokeapp.helper.loadImage
import com.kemsky.pokeapp.ui.activity.detailpokemon.DetailActivity


class FavPokemonAdapter :
    ListAdapter<GetAllPokemonQuery.Pokemon_v2_pokemon, FavPokemonAdapter.FavPokemonViewHolder>(
        DiffCallback
    ) {
    private var states = arrayOf(
        intArrayOf(R.attr.state_lifted),
        intArrayOf(-R.attr.state_liftable),
        intArrayOf(-R.attr.state_above_anchor),
        intArrayOf(R.attr.state_dragged),
    )

    private var colors = intArrayOf(
        Color.MAGENTA,
        Color.MAGENTA,
        Color.MAGENTA,
        Color.MAGENTA
    )

    private var chipColor = ColorStateList(states, colors)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavPokemonViewHolder {
//        return FavPokemonViewHolder(ItemPokemonBinding.inflate(LayoutInflater.from(parent.context)))
        return FavPokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavPokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bindPokemon(pokemon)
    }

    inner class FavPokemonViewHolder(private var binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindPokemon(item: GetAllPokemonQuery.Pokemon_v2_pokemon) = with(binding) {
            pokemonImage.loadImage(getImageUrl(item.id))
            pokemonName.text = item.name.replaceFirstChar(Char::titlecase)
            linearChip.removeAllViews()
            item.pokemon_v2_pokemontypes.forEach {
                val chip = Chip(this.root.context)
                chip.apply {
                    text = it.pokemon_v2_type?.name?.replaceFirstChar(Char::titlecase)
                    chipBackgroundColor = chipColor
                }
                linearChip.addView(chip)
            }
            this.root.setOnClickListener {
                Intent(this.root.context, DetailActivity::class.java).also {
                    it.putExtra("poke_id", item.id)
                    it.putExtra("poke_name", item.name)
                    this.root.context.startActivity(it)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GetAllPokemonQuery.Pokemon_v2_pokemon>() {
        override fun areItemsTheSame(
            oldItem: GetAllPokemonQuery.Pokemon_v2_pokemon,
            newItem: GetAllPokemonQuery.Pokemon_v2_pokemon
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GetAllPokemonQuery.Pokemon_v2_pokemon,
            newItem: GetAllPokemonQuery.Pokemon_v2_pokemon
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

}