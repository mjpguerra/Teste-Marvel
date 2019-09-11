package com.marioguerra.marvelapp.app.ui.characters.source

import androidx.lifecycle.MutableLiveData
import com.marioguerra.marvelapp.app.base.recycler.PaginationDataSource
import com.marioguerra.marvelapp.app.source.CharacterDataSource
import com.marioguerra.marvelapp.data.model.character.Character
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class CharactersPagedDataSource(
    private val characterDataSource: CharacterDataSource,
    initialLoading: MutableLiveData<Boolean>,
    pagination: MutableLiveData<Boolean>,
    error: MutableLiveData<Throwable>
) : PaginationDataSource<Character>(initialLoading, pagination, error) {

    override fun loadInitialData(params: LoadInitialParams, callback: LoadInitialCallback<Character>) {
        val characters = getCharacters(params.pageSize, params.requestedStartPosition)
        callback.onResult(characters, 0)
    }

    override fun loadRangeData(params: LoadRangeParams, callback: LoadRangeCallback<Character>) {
        val characters = getCharacters(params.loadSize, params.startPosition)
        callback.onResult(characters)
    }

    private fun getCharacters(limit: Int, offset: Int): List<Character> {
        return characterDataSource.getCharacters(limit, offset).blockingGet()
    }

}