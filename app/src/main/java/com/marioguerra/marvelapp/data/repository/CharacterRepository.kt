package com.marioguerra.marvelapp.data.repository

import com.marioguerra.marvelapp.app.source.CharacterDataSource
import com.marioguerra.marvelapp.data.model.character.Character
import com.marioguerra.marvelapp.data.model.character.CharacterInfo
import com.marioguerra.marvelapp.data.network.CharacterService
import com.marioguerra.marvelapp.data.network.RequestUtils
import io.reactivex.Single
/**
 * @author Mario Guerra on 11/09/2019
 */

class CharacterRepository(private val characterService: CharacterService, private val requestUtils: RequestUtils
) : CharacterDataSource {

    override fun getCharacters(limit: Int, offset: Int): Single<List<Character>> {
        return requestUtils.prepareRequest(
            characterService.getCharacters(limit, offset)
            .map {
                it.data.results
            })
    }

    override fun getCharacterInfo(id: Int): Single<CharacterInfo> {
        return requestUtils.prepareRequest(
            characterService.getCharacterInfo(id)
            .map {
                it.data.results.first()
            })
    }

}