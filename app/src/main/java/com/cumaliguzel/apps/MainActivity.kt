package com.cumaliguzel.apps

import com.cumaliguzel.apps.screens.WeatherAndClothesPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.cumaliguzel.apps.ui.theme.AppsTheme
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.WeatherViewModel

class MainActivity : ComponentActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var clothesViewModel: ClothesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEdgeToEdge()
        initializeViewModels()
        setContent {
            renderUI()
        }
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
    }

    private fun initializeViewModels() {
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        clothesViewModel = ViewModelProvider(this)[ClothesViewModel::class.java]
    }

    @Composable
    private fun renderUI() {
        AppsTheme {
            WeatherAndClothesPage(
                weatherViewModel = weatherViewModel,
                clothesViewModel = clothesViewModel
            )
        }
    }
}
