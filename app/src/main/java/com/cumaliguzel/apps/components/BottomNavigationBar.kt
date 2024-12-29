package com.cumaliguzel.apps.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import com.cumaliguzel.apps.R

@Composable
fun BottomNavigationBar(selectedTab: Int?, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .size(105.dp)
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = stringResource(id = R.string.bottom_navigation_home_label), tint = MaterialTheme.colorScheme.tertiary) },
            label = { Text(stringResource(id = R.string.bottom_navigation_home_label), color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = stringResource(id = R.string.bottom_navigation_favorites_label), tint = MaterialTheme.colorScheme.tertiary) },
            label = { Text(stringResource(id = R.string.bottom_navigation_favorites_label), color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = stringResource(id = R.string.bottom_navigation_best_label), tint = MaterialTheme.colorScheme.tertiary) },
            label = { Text(stringResource(id = R.string.bottom_navigation_best_label), color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
