package com.marioguerra.marvelapp.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
/**
 * @author Mario Guerra on 11/09/2019
 */

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

}