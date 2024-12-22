package com.cumaliguzel.apps.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cumaliguzel.fitweather.animations.LottieAnimationComposable

@Composable
fun OnBoardingGraphUI(onBoardingModel: OnBoardingModel) {

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
            text = onBoardingModel.title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.fillMaxWidth().size(15.dp))

        // Description text
        Text(
            text = onBoardingModel.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.fillMaxWidth().size(5.dp))
    }
}


//Preview part each pages u can see easily how it looks like :)

//First page preview
@Preview(showBackground = true)
@Composable
fun OnBoardingGraphUIPreview1() {
    OnBoardingGraphUI(onBoardingModel = OnBoardingModel.FirstPages)
}

@Preview(showBackground = true)
@Composable
fun OnBoardingGraphUIPreview2() {
    OnBoardingGraphUI(onBoardingModel = OnBoardingModel.SecondPages)
}

@Preview(showBackground = true)
@Composable
fun OnBoardingGraphUIPreview3() {
    OnBoardingGraphUI(onBoardingModel = OnBoardingModel.ThirdPages)
}

@Preview(showBackground = true)
@Composable
fun OnBoardingGraphUIPreview4() {
    OnBoardingGraphUI(onBoardingModel = OnBoardingModel.ForthPages)
}
@Preview(showBackground = true)
@Composable
fun OnBoardingGraphUIPreview5() {
    OnBoardingGraphUI(onBoardingModel = OnBoardingModel.FifthPages)
}
