// MainActivity.kt
package com.cumaliguzel.apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cumaliguzel.apps.onboarding.OnBoardingUtils
import com.cumaliguzel.apps.onboarding.OnboardingScreen
import com.cumaliguzel.apps.screens.*
import com.cumaliguzel.apps.ui.theme.AppsTheme
import com.cumaliguzel.apps.viewModel.*
import com.cumaliguzel.fitweather.animations.LottieAnimationComposable

class MainActivity : ComponentActivity() {

    private val onBoardingUtils by lazy { OnBoardingUtils(this) }
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var clothesViewModel: ClothesViewModel
    private lateinit var bestClothesViewModel: BestClothesViewModel
    private lateinit var commentsViewModel: CommentsViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEdgeToEdge()
        initializeViewModels()
        setContent {
            RenderUI()
        }
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
    }

    private fun initializeViewModels() {
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        bestClothesViewModel = ViewModelProvider(this)[BestClothesViewModel::class.java]
        val clothesViewModelFactory = ClothesViewModelFactory(applicationContext)
        clothesViewModel = ViewModelProvider(this, clothesViewModelFactory)[ClothesViewModel::class.java]
        commentsViewModel = ViewModelProvider(this)[CommentsViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    @Composable
    private fun RenderUI() {
        AppsTheme {
            var isOnboardingCompleted by remember { mutableStateOf(onBoardingUtils.isOnboardingCompleted()) }
            val authState by authViewModel.authState.observeAsState()

            if (isOnboardingCompleted) {
                when (authState) {
                    is AuthState.UnAuthenticated -> AuthNavigation(authViewModel)
                    is AuthState.Authenticated -> MainScreen(
                        weatherViewModel = weatherViewModel,
                        clothesViewModel = clothesViewModel,
                        bestClothesViewModel = bestClothesViewModel,
                        commentsViewModel = commentsViewModel,
                        authViewModel = authViewModel
                    )
                    else -> LoadingScreen()
                }
            } else {
                ShowOnboardingScreen {
                    onBoardingUtils.setOnboardingCompleted()
                    isOnboardingCompleted = true
                }
            }
        }
    }

    @Composable
    private fun ShowOnboardingScreen(onFinished: () -> Unit) {
        OnboardingScreen {
            onFinished()
        }
    }
}


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

@Composable
fun AuthNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(navController = navController, authViewModel = authViewModel)
        }
        composable("signup") {
            SignupPage(navController = navController, authViewModel = authViewModel)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
       LottieAnimationComposable(animationResId = R.raw.lottie_eror_animation, modifier = Modifier.align(
           Alignment.Center))
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int?, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .size(120.dp)
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
            .background(MaterialTheme.colorScheme.onTertiary)
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.background) },
            label = { Text("Home", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", tint = MaterialTheme.colorScheme.background) },
            label = { Text("Favorites", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Best", tint = MaterialTheme.colorScheme.background) },
            label = { Text("Best", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
