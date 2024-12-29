package com.cumaliguzel.apps.onboarding

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cumaliguzel.fitweather.animations.LottieAnimationComposable

@Composable
fun OnBoardingGraphUI(onBoardingModel: OnBoardingModel) {
    val context = LocalContext.current // `Context`'i burada elde ediyoruz.

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Lottie Animation
        onBoardingModel.animationResId?.let { animationResId ->
            LottieAnimationComposable(
                animationResId = animationResId,
                size = 300.dp
            )
        }
        Spacer(modifier = Modifier.size(70.dp))

        // Title text
        Text(
            text = onBoardingModel.title(context),
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.fillMaxWidth().size(15.dp))

        // Description text
        Text(
            text = onBoardingModel.description(context),
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.fillMaxWidth().size(5.dp))
    }
}
