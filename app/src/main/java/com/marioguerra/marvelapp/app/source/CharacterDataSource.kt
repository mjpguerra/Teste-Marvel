package com.marioguerra.marvelapp.app.source

import com.marioguerra.marvelapp.data.model.character.Character
import com.marioguerra.marvelapp.data.model.character.CharacterInfo
import io.reactivex.Single

interface CharacterDataSource {

    fun getCharacters(limit: Int, offset: Int): Single<List<Character>>

    fun getCharacterInfo(id: Int): Single<CharacterInfo>

}