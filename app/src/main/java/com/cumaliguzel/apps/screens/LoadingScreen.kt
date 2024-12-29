package com.cumaliguzel.apps.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.viewModel.AuthState
import com.cumaliguzel.apps.animations.LottieAnimationComposable

@Composable
fun LoadingScreen(authState: AuthState?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (authState) {
            is AuthState.UnAuthenticated -> {
                LottieAnimationComposable(
                    animationResId = R.raw.lottie_eror_animation,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is AuthState.Loading -> {
                LottieAnimationComposable(
                    animationResId = R.raw.animation_loading,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LottieAnimationComposable(
                    animationResId = R.raw.lottie_eror_animation,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}