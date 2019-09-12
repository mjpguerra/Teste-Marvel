package com.marioguerra.marvelapp.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * @author Mario Guerra on 11/09/2019
 */
class DataContainer<T>(
    @SerializedName("results")
    val results: List<T>
)