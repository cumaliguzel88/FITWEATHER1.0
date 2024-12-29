package com.cumaliguzel.apps.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize
import com.cumaliguzel.apps.animations.LottieAnimationComposable

@Composable
fun OnBoardingGraphUI(onBoardingModel: OnBoardingModel) {
    val context = LocalContext.current
    val windowSize = rememberWindowSize() // Dinamik pencere boyutu alıyoruz.

    if (windowSize.width == WindowType.Expanded) {
        // Landscape mode (Yatay)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Animasyon
            onBoardingModel.animationResId?.let { animationResId ->
                LottieAnimationComposable(
                    animationResId = animationResId,
                    size = 200.dp // Yatay mod için daha küçük boyut
                )
            }

            // Metinler
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                // Başlık
                Text(
                    text = onBoardingModel.title(context),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Açıklama
                Text(
                    text = onBoardingModel.description(context),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    } else {
        // Portrait mode (Dikey)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animasyon
            onBoardingModel.animationResId?.let { animationResId ->
                LottieAnimationComposable(
                    animationResId = animationResId,
                    size = 300.dp // Dikey mod için daha büyük boyut
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Başlık
            Text(
                text = onBoardingModel.title(context),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Açıklama
            Text(
                text = onBoardingModel.description(context),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
