package com.kemsky.pokeapp.constant

object AppConstant {

    const val BASE_URL = "https://beta.pokeapi.co/graphql/v1beta"

    fun getImageUrl(pokeId: Int?): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$pokeId.png"
}