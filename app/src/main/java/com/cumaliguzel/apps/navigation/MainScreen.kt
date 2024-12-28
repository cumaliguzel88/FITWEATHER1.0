package com.cumaliguzel.apps.navigation
import com.cumaliguzel.apps.screens.BestPage
import com.cumaliguzel.apps.screens.DetailScreen
import com.cumaliguzel.apps.screens.FavoritesPage
import com.cumaliguzel.apps.screens.WeatherAndClothesPage
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cumaliguzel.apps.components.BottomNavigationBar
import com.cumaliguzel.apps.viewModel.AuthViewModel
import com.cumaliguzel.apps.viewModel.BestClothesViewModel
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.CommentsViewModel
import com.cumaliguzel.apps.viewModel.WeatherViewModel

@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel,
    bestClothesViewModel: BestClothesViewModel,
    commentsViewModel: CommentsViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = null,
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
                    navController = navController,
                    authViewModel = authViewModel
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


