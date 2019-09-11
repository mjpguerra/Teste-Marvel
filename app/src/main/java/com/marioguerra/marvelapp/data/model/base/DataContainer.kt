package com.marioguerra.marvelapp.data.model.base

import com.google.gson.annotations.SerializedName

class DataContainer<T>(
    @SerializedName("results")
    val results: List<T>
)