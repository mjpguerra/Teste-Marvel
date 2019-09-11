package com.marioguerra.marvelapp.data.model.character

import com.marioguerra.marvelapp.data.model.Image
import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("thumbnail") val image: Image?
)