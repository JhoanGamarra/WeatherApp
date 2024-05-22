package com.jhoangamarra.wethearchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jhoangamarra.wethearchallenge.lib.LocalViewModelStore
import com.jhoangamarra.wethearchallenge.lib.ViewModelStore
import com.jhoangamarra.wethearchallenge.screens.landingScreen
import com.jhoangamarra.wethearchallenge.ui.theme.WethearChallengeTheme
import com.jhoangamarra.wethearchallenge.weather.navigation.WelcomeRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val viewModelStore = remember { ViewModelStore() }

            WethearChallengeTheme {

                CompositionLocalProvider(
                    LocalNavControllerProvider provides navController,
                    LocalViewModelStore provides viewModelStore
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = WelcomeRoute.route
                    ) {
                        //welcomeScreen(this@MainActivity)
                        landingScreen()
                    }
                }
            }
        }
    }

}

internal val LocalNavControllerProvider = staticCompositionLocalOf<NavController> {
    error("You must provide a NavController before attempt to use it")
}