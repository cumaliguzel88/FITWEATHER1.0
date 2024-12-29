package com.cumaliguzel.apps.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.animations.LottieAnimationComposable

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


