package com.marioguerra.marvelapp.app.ui.character_info

import android.os.Bundle
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.base.ui.BaseActivity
import com.marioguerra.marvelapp.app.navigation.MainNavigator
import com.marioguerra.marvelapp.app.navigation.MainNavigatorProvider
import com.marioguerra.marvelapp.app.ui.characters.CharactersFragment
import com.marioguerra.marvelapp.app.ui.utils.showCustomDialog


class CharacterInfoActivity : BaseActivity(), MainNavigatorProvider {

    private lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras

        mainNavigator = MainNavigator(supportFragmentManager, R.id.flMainContainer)

        if (savedInstanceState == null) {
            val characterInfoFragment = CharacterInfoFragment.newInstance(
                bundle.getInt("id"),
                bundle.getString("name"),
                bundle.getString("image")
            )

            mainNavigator.addFragment(characterInfoFragment, false, CharactersFragment.TAG)
        }

    }

    override fun provideMainNavigator(): MainNavigator {
        return mainNavigator
    }




}
