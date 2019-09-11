package com.marioguerra.marvelapp.data.network

import com.marioguerra.marvelapp.data.model.base.DataWrapper
import com.marioguerra.marvelapp.data.model.character.Character
import com.marioguerra.marvelapp.data.model.character.CharacterInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<DataWrapper<Character>>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacterInfo(@Path("characterId") id: Int): Single<DataWrapper<CharacterInfo>>

}