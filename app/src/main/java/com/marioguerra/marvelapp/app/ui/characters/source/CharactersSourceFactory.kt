package com.marioguerra.marvelapp.app.ui.characters.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.marioguerra.marvelapp.app.source.CharacterDataSource
import com.marioguerra.marvelapp.data.model.character.Character

class CharactersSourceFactory(
    private val charactersDataSource: CharacterDataSource,
    private val loading: MutableLiveData<Boolean>,
    private val pagination: MutableLiveData<Boolean>,
    private val error: MutableLiveData<Throwable>
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersPagedDataSource(
            charactersDataSource,
            loading,
            pagination,
            error
        )
    }

}