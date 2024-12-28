
package com.cumaliguzel.apps
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.cumaliguzel.apps.navigation.AuthNavigation
import com.cumaliguzel.apps.navigation.MainScreen
import com.cumaliguzel.apps.onboarding.OnBoardingUtils
import com.cumaliguzel.apps.onboarding.OnboardingScreen
import com.cumaliguzel.apps.screens.*
import com.cumaliguzel.apps.ui.theme.AppsTheme
import com.cumaliguzel.apps.viewModel.*


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
                    else -> LoadingScreen(authState)
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

