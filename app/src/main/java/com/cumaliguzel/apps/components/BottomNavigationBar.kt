package com.cumaliguzel.apps.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize

@Composable
fun BottomNavigationBar(
    selectedTab: Int?,
    onTabSelected: (Int) -> Unit
) {
    val windowSize = rememberWindowSize()


    val navigationBarHeight = when (windowSize.height) {
        WindowType.Compact -> 90.dp
        WindowType.Medium -> 99.dp
        WindowType.Expanded -> 110.dp
    }

    val fontSize = when (windowSize.width) {
        WindowType.Compact -> 13.sp
        WindowType.Medium -> 14.sp
        WindowType.Expanded -> 16.sp
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
            .height(navigationBarHeight)
    ) {

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = R.string.bottom_navigation_home_label),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.bottom_navigation_home_label),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                )
            },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )


        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.bottom_navigation_favorites_label),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.bottom_navigation_favorites_label),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                )
            },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )


        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(id = R.string.bottom_navigation_best_label),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.bottom_navigation_best_label),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                )
            },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
