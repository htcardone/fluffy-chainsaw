package com.podium.technicalchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.podium.technicalchallenge.navigation.MyNavHost
import com.podium.technicalchallenge.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { 
            MyTheme {
                val navController = rememberNavController()
                MyNavHost(navController = navController)
            }
        }

    }
}