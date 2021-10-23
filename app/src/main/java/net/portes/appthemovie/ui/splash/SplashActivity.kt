package net.portes.appthemovie.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.portes.appthemovie.ui.main.MainActivity

/**
 * @author amadeus.portes
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.launch(this)
    }
}