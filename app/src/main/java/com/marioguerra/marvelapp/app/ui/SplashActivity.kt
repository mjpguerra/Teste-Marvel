package com.marioguerra.marvelapp.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.navigation.MainNavigator
import com.marioguerra.marvelapp.app.ui.characters.CharacterActivity
import com.marioguerra.marvelapp.app.ui.characters.CharactersFragment

/**
 * @author Mario Guerra on 11/09/2019
 */

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Handler().postDelayed({
                val mIntent = Intent(baseContext, CharacterActivity::class.java)
                startActivity(mIntent)
                finish()
            },
            TIME_OUT.toLong()
        )
    }

    companion object {
        private const val TIME_OUT = 2000
    }
}
