package com.kemsky.pokeapp.ui.allpokemon.adapter

//import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kemsky.GetPagedPokemonQuery
import com.kemsky.pokeapp.R
import com.kemsky.pokeapp.constant.AppConstant.getImageUrl
import com.kemsky.pokeapp.databinding.ItemPokemonBinding
import com.kemsky.pokeapp.helper.loadImage


class AllPokemonAdapter :
    PagingDataAdapter<GetPagedPokemonQuery.Pokemon_v2_pokemon, AllPokemonAdapter.AllPokemonViewHolder>(
        AllPokemonComparator
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllPokemonViewHolder {
        return AllPokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: AllPokemonViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPokemon(it) }
    }

    inner class AllPokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

        fun bindPokemon(item: GetPagedPokemonQuery.Pokemon_v2_pokemon) = with(binding) {
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
        }
    }

    object AllPokemonComparator : DiffUtil.ItemCallback<GetPagedPokemonQuery.Pokemon_v2_pokemon>() {
        override fun areItemsTheSame(
            oldItem: GetPagedPokemonQuery.Pokemon_v2_pokemon,
            newItem: GetPagedPokemonQuery.Pokemon_v2_pokemon
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetPagedPokemonQuery.Pokemon_v2_pokemon,
            newItem: GetPagedPokemonQuery.Pokemon_v2_pokemon
        ): Boolean {
            return oldItem == newItem
        }
    }
}