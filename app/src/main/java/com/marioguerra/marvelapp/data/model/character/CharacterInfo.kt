package com.marioguerra.marvelapp.data.model.character

import com.google.gson.annotations.SerializedName
/**
 * @author Mario Guerra on 11/09/2019
 */
class CharacterInfo(
    @SerializedName("description")
    val description: String?
)