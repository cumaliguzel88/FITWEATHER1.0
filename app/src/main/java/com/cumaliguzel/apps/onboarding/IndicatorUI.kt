package com.cumaliguzel.apps.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorUI(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.tertiary,
    indicatorSize: Dp = 14.dp
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(pageSize) {
            Spacer(modifier = Modifier.size(2.dp))
            Box(
                modifier = Modifier
                    .height(indicatorSize)
                    .width(if (it == currentPage) (indicatorSize * 2.5f) else indicatorSize)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = if (it == currentPage) selectedColor else unselectedColor)
            )
            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}
