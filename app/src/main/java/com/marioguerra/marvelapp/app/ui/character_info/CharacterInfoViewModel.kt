package com.marioguerra.marvelapp.app.ui.character_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marioguerra.marvelapp.app.base.observeOnUI
import com.marioguerra.marvelapp.app.base.subscribeOnIO
import com.marioguerra.marvelapp.app.base.viewmodel.BaseViewModel
import com.marioguerra.marvelapp.app.source.CharacterDataSource
import com.marioguerra.marvelapp.data.model.character.CharacterInfo

/**
 * @author Mario Guerra on 11/09/2019
 */

class CharacterInfoViewModel(private val characterId: Int, private val characterDataSource: CharacterDataSource) : BaseViewModel() {

    private val characterInfo = MutableLiveData<CharacterInfo>()

    init {
        loading.value = true
        unsubscribeOnClear(
            characterDataSource.getCharacterInfo(characterId)
                .subscribeOnIO()
                .observeOnUI()
                .subscribe(this::onCharacterInfoLoaded, this::onError)
        )
    }

    private fun onCharacterInfoLoaded(characterInfo: CharacterInfo) {
        loading.value = false
        this.characterInfo.value = characterInfo
    }

    private fun onError(throwable: Throwable) {
        error.value = throwable
    }

    fun getCharacterInfo(): LiveData<CharacterInfo> {
        return characterInfo
    }

    class Factory(private val id: Int, private val characterDataSource: CharacterDataSource) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterInfoViewModel(id, characterDataSource) as T
        }

    }

}
