package com.cumaliguzel.apps.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cumaliguzel.apps.R
import com.cumaliguzel.fitweather.animations.LottieAnimationComposable

@Composable
fun EmptyFavoritesView() {
   Column(
       modifier = Modifier.fillMaxSize(),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       //lottie no data
       LottieAnimationComposable(
           animationResId = R.raw.lottie_no_data,
           modifier = Modifier.fillMaxSize()
       )
   }
}


