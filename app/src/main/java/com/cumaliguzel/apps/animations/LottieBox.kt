package com.cumaliguzel.apps.animations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieBox(
    modifier: Modifier = Modifier,
    animation: Int,
    iterations: Int = LottieConstants.IterateForever
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(animation)
        )
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(300.dp),
            iterations = iterations
        )
    }
}
