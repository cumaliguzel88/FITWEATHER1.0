package com.cumaliguzel.apps.animations
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationComposable(
    animationResId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 300.dp,
    iterations: Int = LottieConstants.IterateForever
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = iterations
        )
    }
}
