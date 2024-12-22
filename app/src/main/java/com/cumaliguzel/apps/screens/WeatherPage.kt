package com.cumaliguzel.apps.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.api.NetworkResponse
import com.cumaliguzel.apps.components.ClothesCard
import com.cumaliguzel.apps.components.GenderSelectionDropdown
import com.cumaliguzel.apps.components.WeatherDetails
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.WeatherViewModel
import com.cumaliguzel.fitweather.animations.LottieAnimationComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAndClothesPage(
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel,
    navController: NavController
) {
    val weatherResult by weatherViewModel.weatherResult.observeAsState()
    val clothesList by clothesViewModel.clothesList.collectAsStateWithLifecycle(emptyList())
    val selectedGender by clothesViewModel.gender.collectAsState()
    val context = LocalContext.current

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            weatherViewModel.fetchLocation(context, weatherViewModel)
        } else {
            println("Location permission denied.")
        }
    }

    LaunchedEffect(Unit) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            weatherViewModel.fetchLocation(context, weatherViewModel)
        } else {
            locationPermissionLauncher.launch(permission)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                when (val result = weatherResult) {
                    is NetworkResponse.Error -> {
                      LottieAnimationComposable(animationResId = R.raw.eror_animation,Modifier.fillMaxSize().align(Alignment.CenterHorizontally))
                    }
                    is NetworkResponse.Loading -> {
                        LottieAnimationComposable(animationResId = R.raw.animation_loading,Modifier.fillMaxSize().align(Alignment.CenterHorizontally))
                    }
                    is NetworkResponse.Success -> {
                        WeatherDetails(
                            data = result.data,
                            weatherViewModel = weatherViewModel,
                            clothesViewModel = clothesViewModel
                        )
                    }
                    null -> {}
                }
            }

            if (weatherResult is NetworkResponse.Success) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Spacer(modifier = Modifier.height(5.dp))
                    GenderSelectionDropdown(
                        selectedGender = selectedGender,
                        onGenderSelected = { clothesViewModel.setGender(it) }
                    )
                }

                items(clothesList) { clothes ->
                    ClothesCard(
                        clothes = clothes,
                        isFavorite = clothes.isFavorite,
                        onToggleFavorite = { clothesViewModel.toggleFavorite(clothes) },
                        onClick = {
                            navController.navigate("detail_screen/${clothes.id}")
                        }
                    )
                }
            }
        }
    }
}