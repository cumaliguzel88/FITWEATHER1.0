package com.cumaliguzel.apps.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages = listOf(
        OnBoardingModel.FirstPages,
        OnBoardingModel.SecondPages,
        OnBoardingModel.ThirdPages,
        OnBoardingModel.ForthPages,
        OnBoardingModel.FifthPages
    )

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    val scope = rememberCoroutineScope()

    val windowSize = rememberWindowSize()

    val buttonFontSize = when (windowSize.width) {
        WindowType.Compact -> 14.sp
        WindowType.Medium -> 16.sp
        WindowType.Expanded -> 18.sp
    }

    val indicatorSize = when (windowSize.width) {
        WindowType.Compact -> 12.dp
        WindowType.Medium -> 14.dp
        WindowType.Expanded -> 16.dp
    }

    val bottomPadding = when (windowSize.height) {
        WindowType.Compact -> 40.dp
        WindowType.Medium -> 60.dp
        WindowType.Expanded -> 70.dp
    }

    Scaffold(bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, bottomPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (pagerState.currentPage > 0) {
                    ButtonUI(
                        text = stringResource(id = R.string.onboarding_button_back),
                        backgroundColor = Color.Transparent,
                        textColor = Color.Gray,
                        fontSize = buttonFontSize.value
                    ) {
                        scope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                }
            }

            // Indicators
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                IndicatorUI(
                    pageSize = pages.size,
                    currentPage = pagerState.currentPage,
                    indicatorSize = indicatorSize
                )
            }

            // Next or Start button
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                val nextButtonText = if (pagerState.currentPage == pages.size - 1) {
                    stringResource(id = R.string.onboarding_button_start) // "Start" olacak
                } else {
                    stringResource(id = R.string.onboarding_button_next) // Diğer sayfalar için "Next"
                }
                ButtonUI(
                    text = nextButtonText,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    fontSize = buttonFontSize.value
                ) {
                    scope.launch {
                        if (pagerState.currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onFinished()
                        }
                    }
                }
            }
        }
    }) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(bottom = 20.dp)) {
            HorizontalPager(state = pagerState) { index ->
                OnBoardingGraphUI(onBoardingModel = pages[index])
            }
        }
    }
}
