package com.marioguerra.marvelapp.app.source

import com.marioguerra.marvelapp.data.model.character.Character
import com.marioguerra.marvelapp.data.model.character.CharacterInfo
import io.reactivex.Single
/**
 * @author Mario Guerra on 11/09/2019
 */

interface CharacterDataSource {

    fun getCharacters(limit: Int, offset: Int): Single<List<Character>>

    fun getCharacterInfo(id: Int): Single<CharacterInfo>

}