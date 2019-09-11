package com.marioguerra.marvelapp.app.di.module

import com.marioguerra.marvelapp.app.source.CharacterDataSource
import com.marioguerra.marvelapp.data.network.CharacterService
import com.marioguerra.marvelapp.data.network.RequestUtils
import com.marioguerra.marvelapp.data.repository.CharacterRepository
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    fun provideCharacterRepository(
        characterService: CharacterService,
        requestUtils: RequestUtils
    ): CharacterDataSource {
        return CharacterRepository(characterService, requestUtils)
    }

}