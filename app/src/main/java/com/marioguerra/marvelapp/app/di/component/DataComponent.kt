package com.marioguerra.marvelapp.app.di.component

import com.marioguerra.marvelapp.app.di.module.RepositoryModule
import com.marioguerra.marvelapp.app.source.CharacterDataSource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface DataComponent {

    fun provideCharacterRepository(): CharacterDataSource

}