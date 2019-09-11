package com.marioguerra.marvelapp

import android.app.Application
import com.marioguerra.marvelapp.app.di.component.DaggerDataComponent
import com.marioguerra.marvelapp.app.di.component.DataComponent
import com.marioguerra.marvelapp.app.di.module.ContextModule

class App : Application() {

    private lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()

        val contextModule = ContextModule(this)

        dataComponent = DaggerDataComponent.builder()
            .contextModule(contextModule)
            .build()

    }

    fun provideDataComponent(): DataComponent {
        return dataComponent
    }

}