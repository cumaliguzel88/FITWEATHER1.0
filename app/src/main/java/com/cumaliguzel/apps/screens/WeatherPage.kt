package com.cumaliguzel.apps.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Woman
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.api.NetworkResponse
import com.cumaliguzel.apps.api.WeatherModel
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAndClothesPage(
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel
) {
    val weatherResult = weatherViewModel.weatherResult.observeAsState()
    val clothesList by clothesViewModel.clothesList.collectAsStateWithLifecycle(emptyList())
    val selectedGender by clothesViewModel.gender.collectAsState()
    val context = LocalContext.current

    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedClothes by remember { mutableStateOf<Clothes?>(null) }

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
                when (val result = weatherResult.value) {
                    is NetworkResponse.Error -> {
                        Spacer(modifier = Modifier.height(26.dp))
                        Text(text = "Error: ${result.message}", fontWeight = FontWeight.Bold)
                    }
                    is NetworkResponse.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is NetworkResponse.Success -> {
                        WeatherDetails(data = result.data, weatherViewModel, clothesViewModel)
                    }
                    null -> {}
                }
            }

            if (weatherResult.value is NetworkResponse.Success) {
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
                            selectedClothes = clothes
                            isBottomSheetVisible = true
                        }
                    )
                }
            }
        }
    }

    if (isBottomSheetVisible && selectedClothes != null) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            modifier = Modifier.fillMaxHeight(0.95f)
        ) {
            ClothesDetailsBottomSheet(
                clothes = selectedClothes!!,
                clothesViewModel = clothesViewModel
            )
        }
    }
}


@Composable
fun GenderSelectionDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { expanded = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (selectedGender == "male") Icons.Default.Man else Icons.Default.Woman,
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Gender: ${if (selectedGender == "male") "Male" else "Female"}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Male") },
                onClick = {
                    onGenderSelected("male")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Female") },
                onClick = {
                    onGenderSelected("female")
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun ClothesCard(
    clothes: Clothes,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Büyük resim
            AsyncImage(
                model = clothes.img,
                contentDescription = "Image of clothes",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp) // Resim alanını büyüttük
                    .align(Alignment.TopCenter) // Ortalıyoruz
            )

            // Favori ikonu sağ üst köşede
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
fun ClothesDetailsBottomSheet(clothes: Clothes, clothesViewModel: ClothesViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // TextButton'lar (Altta)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top Link TextButton
            TextButton(
                onClick = { clothesViewModel.openTopLink(clothes.topLink) }
            ) {
                Text(text = "👕 View Top Link", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Bottom Link TextButton
            TextButton(
                onClick = { clothesViewModel.openBottomLink(clothes.bottomLink) }
            ) {
                Text(text = "👖 View Bottom Link", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onTertiary,fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))




        // Kıyafet Görseli (Üstte)
        AsyncImage(
            model = clothes.img,
            contentDescription = "Clothes Image",
            modifier = Modifier
                .fillMaxSize()
                .weight(1f) // Görsel alanı daha esnek hale getirildi
        )



    }
}





@Composable
fun WeatherDetails(
    data: WeatherModel,
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    var cityName by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Hava durumu güncellendiğinde kıyafetleri değiştir
    LaunchedEffect(data.current.temp_c) {
        clothesViewModel.fetchAndUpdateClothes(data) // Hava durumu verisini ClothesViewModel'e gönder
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text("Search for any location: ") }
            )
            IconButton(onClick = {
                weatherViewModel.getData(cityName)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for any location",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = data.location.name, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 10.sp, color = Color.Gray)
        }
        Text(
            text = "${data.current.temp_c} ° C",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = "https:${data.current.condition.icon}",
            contentDescription = "Weather Icon"
        )

        Text(
            text = data.current.condition.text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(5.dp))

        ElevatedCard(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Details"
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        WeatherKeyValue(key = "Humidity", value = "${data.current.humidity}%")
                        WeatherKeyValue(key = "Feels Like", value = "${data.current.feelslike_c}° C")
                    }
                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            WeatherKeyValue(key = "Wind Speed", value = "${data.current.wind_kph} km/h")
                            WeatherKeyValue(key = "Precipitation", value = "${data.current.precip_mm} mm")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            WeatherKeyValue(
                                key = "Local Time",
                                value = data.location.localtime.split(' ')[1]
                            )
                            WeatherKeyValue(
                                key = "Local Date",
                                value = data.location.localtime.split(' ')[0]
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun WeatherKeyValue(key: String, value: String) {
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = key, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}