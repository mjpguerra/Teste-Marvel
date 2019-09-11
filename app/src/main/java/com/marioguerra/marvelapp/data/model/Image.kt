package com.marioguerra.marvelapp.data.model

import com.google.gson.annotations.SerializedName
/**
 * @author Mario Guerra on 11/09/2019
 */
class Image(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
) {
    companion object {
        private const val VARIANT = "portrait_incredible"
    }

    fun getFullPath(): String? {
        return if (path != null && extension != null) {
            "$path/$VARIANT.$extension"
        } else {
            null
        }
    }

}