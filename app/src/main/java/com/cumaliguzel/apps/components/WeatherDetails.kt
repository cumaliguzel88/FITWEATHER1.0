package com.cumaliguzel.apps.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.api.WeatherModel
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.WeatherViewModel


@Composable
fun WeatherDetails(
    data: WeatherModel,
    weatherViewModel: WeatherViewModel,
    clothesViewModel: ClothesViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    var cityName by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(data.current.temp_c) {
        clothesViewModel.fetchAndUpdateClothes(data)
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
                label = { Text(text = stringResource(R.string.weather_details_text_field_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationCity,
                        contentDescription = "Location Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                shape = RoundedCornerShape(size = 15.dp)

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
            Text(text = data.location.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        contentDescription = "Toggle Details",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        WeatherKeyValue(key = stringResource(R.string.weather_details_humidity_label), value = "${data.current.humidity}%")
                        WeatherKeyValue(key = stringResource(R.string.weather_details_feels_like_label), value = "${data.current.feelslike_c}° C")
                    }
                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            WeatherKeyValue(key = stringResource(R.string.weather_details_wind_speed_label), value = "${data.current.wind_kph} km/h")
                            WeatherKeyValue(key = stringResource(R.string.weather_details_precipitation_label), value = "${data.current.precip_mm} mm")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            WeatherKeyValue(
                                key = stringResource(R.string.weather_local_time_label),
                                value = data.location.localtime.split(' ')[1]
                            )
                            WeatherKeyValue(
                                key = stringResource(R.string.weather_local_date_label),
                                value = data.location.localtime.split(' ')[0]
                            )
                        }
                    }
                }
            }
        }
    }
}
