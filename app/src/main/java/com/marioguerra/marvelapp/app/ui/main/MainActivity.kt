package com.marioguerra.marvelapp.app.ui.main

import android.os.Bundle
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.base.ui.BaseActivity
import com.marioguerra.marvelapp.app.navigation.MainNavigator
import com.marioguerra.marvelapp.app.navigation.MainNavigatorProvider
import com.marioguerra.marvelapp.app.ui.characters.CharactersFragment
import com.marioguerra.marvelapp.app.ui.utils.showCustomDialog

class MainActivity : BaseActivity(), MainNavigatorProvider {

    private lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNavigator = MainNavigator(supportFragmentManager, R.id.flMainContainer)

        if (savedInstanceState == null) {
            val charactersFragment = CharactersFragment.newInstance()
            mainNavigator.addFragment(charactersFragment, false, CharactersFragment.TAG)
        }

    }

    override fun provideMainNavigator(): MainNavigator {
        return mainNavigator
    }


    override fun onBackPressed() {
        showCustomDialog(image = R.drawable.ic_warning, title =
        R.string.dialog_exit_message,
            no = {}, noDismissesDialog = true, yes =
            {
                super.onBackPressed()
                finish()
            })

    }
}
