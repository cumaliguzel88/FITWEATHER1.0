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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(selectedTab: Int?, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .size(105.dp)
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.background) },
            label = { Text("Home", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", tint = MaterialTheme.colorScheme.background) },
            label = { Text("Favorites", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Best", tint = MaterialTheme.colorScheme.background) },
            label = { Text("Best", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold) },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
