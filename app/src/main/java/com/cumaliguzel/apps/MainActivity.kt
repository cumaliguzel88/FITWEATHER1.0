// MainActivity.kt
package com.cumaliguzel.apps

import com.cumaliguzel.apps.screens.WeatherAndClothesPage
import com.cumaliguzel.apps.screens.FavoritesPage
import com.cumaliguzel.apps.screens.BestPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cumaliguzel.apps.screens.DetailScreen
import com.cumaliguzel.apps.ui.theme.AppsTheme
import com.cumaliguzel.apps.viewModel.BestClothesViewModel
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.ClothesViewModelFactory
import com.cumaliguzel.apps.viewModel.WeatherViewModel
import com.cumaliguzel.apps.viewModel.CommentsViewModel

class MainActivity : ComponentActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var clothesViewModel: ClothesViewModel
    private lateinit var bestClothesViewModel: BestClothesViewModel
    private lateinit var commentsViewModel: CommentsViewModel

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
        bestClothesViewModel = ViewModelProvider(this)[BestClothesViewModel::class.java]

        // ViewModelFactory ile ClothesViewModel oluşturuluyor
        val clothesViewModelFactory = ClothesViewModelFactory(applicationContext)
        clothesViewModel = ViewModelProvider(this, clothesViewModelFactory)[ClothesViewModel::class.java]

        // CommentsViewModel oluşturuluyor
        commentsViewModel = ViewModelProvider(this)[CommentsViewModel::class.java]
    }

    @Composable
    private fun renderUI() {
        AppsTheme {
            MainScreen(
                weatherViewModel = weatherViewModel,
                clothesViewModel = clothesViewModel,
                bestClothesViewModel = bestClothesViewModel,
                commentsViewModel = commentsViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel,
    bestClothesViewModel: BestClothesViewModel,
    commentsViewModel: CommentsViewModel
) {
    val navController = rememberNavController() // NavController oluşturuluyor

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = null, // Navigation ile artık bir `selectedTab` kullanmaya gerek yok
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> navController.navigate("weather_and_clothes")
                        1 -> navController.navigate("favorites")
                        2 -> navController.navigate("best")
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "weather_and_clothes",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("weather_and_clothes") {
                WeatherAndClothesPage(
                    weatherViewModel = weatherViewModel,
                    clothesViewModel = clothesViewModel,
                    navController = navController
                )
            }
            composable("favorites") {
                FavoritesPage(clothesViewModel = clothesViewModel)
            }
            composable("best") {
                BestPage(viewModel = bestClothesViewModel)
            }
            composable(
                "detail_screen/{clothesId}",
                arguments = listOf(navArgument("clothesId") { type = NavType.IntType })
            ) { backStackEntry ->
                val clothesId = backStackEntry.arguments?.getInt("clothesId")
                val clothes = clothesViewModel.getClothesById(clothesId ?: 0)
                if (clothes != null) {
                    DetailScreen(
                        clothesViewModel = clothesViewModel,
                        commentsViewModel = commentsViewModel,
                        clothes = clothes,
                        navController = navController
                    )
                }
            }

        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int?, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onTertiary,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .size(120.dp)
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
            .background(MaterialTheme.colorScheme.onTertiary)
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.onSecondary) },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", tint = MaterialTheme.colorScheme.onSecondary) },
            label = { Text("Favorites") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Best", tint = MaterialTheme.colorScheme.onSecondary) },
            label = { Text("Best") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
