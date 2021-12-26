package com.kemsky.pokeapp.ui.activity.detailpokemon

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.kemsky.pokeapp.R
import com.kemsky.pokeapp.ViewModelFactory
import com.kemsky.pokeapp.constant.AppConstant.getImageUrl
import com.kemsky.pokeapp.databinding.ActivityDetailBinding
import com.kemsky.pokeapp.helper.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.job
import timber.log.Timber
import javax.inject.Inject

// Entry point dependency injection by dagger hilt
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    // injecting dependency (viewmodelfactory)
    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: DetailViewModel

    private var binding: ActivityDetailBinding? = null

    private val states = arrayOf(
        intArrayOf(R.attr.state_lifted),
        intArrayOf(-R.attr.state_liftable),
        intArrayOf(-R.attr.state_above_anchor),
        intArrayOf(R.attr.state_dragged),
    )

    private val colors = intArrayOf(
        Color.MAGENTA,
        Color.MAGENTA,
        Color.MAGENTA,
        Color.MAGENTA
    )

    private val chipColor = ColorStateList(states, colors)

    // get data from previous activity
    private val pokeId by lazy {
        intent.extras?.getInt("poke_id", 0)
    }

    private val pokeName by lazy {
        intent.extras?.getString("poke_name")?.replaceFirstChar(Char::titlecase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflating UI by viewbinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        // instantiating viewmodel with viewmodelprovider with custom factory
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        configureUi()
    }

    private fun configureUi() {
        lifecycleScope.launchWhenStarted {
            pokeId?.let {
                viewModel.fetchDetailPokemon(it).filterNotNull().collect { detailData ->
                    detailData.pokemon_v2_pokemon[0].let { pokemon ->
                        binding?.linearChip?.removeAllViews()
                        pokemon.pokemon_v2_pokemontypes.forEach {
                            val chip = Chip(this@DetailActivity)
                            chip.apply {
                                text = it.pokemon_v2_type?.name?.replaceFirstChar(Char::titlecase)
                                chipBackgroundColor = chipColor
                            }
                            binding?.linearChip?.addView(chip)
                        }

                        binding?.toolbar?.title = pokemon.name.replaceFirstChar(Char::titlecase)
                        binding?.pokemonImage?.loadImage(getImageUrl(pokemon.id))
                        binding?.pokemonName?.text = pokemon.name.replaceFirstChar(Char::titlecase)
                        binding?.textFormSwitchable?.text =
                            if (pokemon.pokemon_v2_pokemonspecy?.forms_switchable == true) "YES" else "NO"
                        binding?.textBaseHappiness?.text =
                            pokemon.pokemon_v2_pokemonspecy?.base_happiness.toString()
                        binding?.textHatchCounter?.text =
                            pokemon.pokemon_v2_pokemonspecy?.hatch_counter.toString()
                        binding?.textCaptureRate?.text =
                            pokemon.pokemon_v2_pokemonspecy?.capture_rate.toString()
                        binding?.textBaseExperience?.text =
                            pokemon.base_experience.toString()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_pokemon -> {
                configureFavoriteButton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configureFavoriteButton() {
        lifecycleScope.launchWhenStarted {
            viewModel.getSingleFav(pokeId)?.collect {
                Timber.tag("favpokemon").d(it.toString())
                if (it?.id != pokeId) {
                    viewModel.addToFavorite(pokeId!!, pokeName!!)
                    Toast.makeText(
                        this@DetailActivity,
                        "$pokeName telah ditambahkan ke Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                    // To stop reactivity in flow
                    this.coroutineContext.job.cancelAndJoin()
                } else {
                    viewModel.deleteFromFavorite(pokeId!!, pokeName!!)
                    Toast.makeText(
                        this@DetailActivity,
                        "$pokeName telah dihapus dari Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                    // To stop reactivity in flow
                    this.coroutineContext.job.cancelAndJoin()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Avoiding memory leak in databinding/viewbinding
        binding = null
    }
}