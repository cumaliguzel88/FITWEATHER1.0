package com.cumaliguzel.apps

import com.cumaliguzel.apps.screens.WeatherAndClothesPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.cumaliguzel.apps.screens.BestPage
import com.cumaliguzel.apps.screens.FavoritesPage
import com.cumaliguzel.apps.ui.theme.AppsTheme
import com.cumaliguzel.apps.viewModel.BestClothesViewModel
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.WeatherViewModel

class MainActivity : ComponentActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var clothesViewModel: ClothesViewModel
    private lateinit var bestClothesViewModel: BestClothesViewModel

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
        bestClothesViewModel = ViewModelProvider(this)[BestClothesViewModel::class.java]
    }

    @Composable
    private fun renderUI() {
        AppsTheme {
            MainScreen(
                weatherViewModel = weatherViewModel,
                clothesViewModel = clothesViewModel,
                bestClothesViewModel = bestClothesViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel,
    bestClothesViewModel: BestClothesViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> WeatherAndClothesPage(
                    weatherViewModel = weatherViewModel,
                    clothesViewModel = clothesViewModel
                )
                1 -> FavoritesPage()
                2 -> BestPage(viewModel = bestClothesViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Best") },
            label = { Text("Best") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
