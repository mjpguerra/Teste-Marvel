package com.marioguerra.marvelapp.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.navigation.MainNavigator
import com.marioguerra.marvelapp.app.ui.characters.CharactersFragment
import com.marioguerra.marvelapp.app.ui.main.MainActivity

class Splash : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        //
        Handler().postDelayed(
            {
                val mIntent = Intent(
                    baseContext,
                    MainActivity::class.java
                )
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
