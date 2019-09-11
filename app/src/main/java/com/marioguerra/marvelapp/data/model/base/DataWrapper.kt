package com.marioguerra.marvelapp.data.model.base

import com.google.gson.annotations.SerializedName

class DataWrapper<T>(
    @SerializedName("data")
    val data: DataContainer<T>
)