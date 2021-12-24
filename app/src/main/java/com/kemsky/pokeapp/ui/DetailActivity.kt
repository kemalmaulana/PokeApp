package com.kemsky.pokeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kemsky.pokeapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}