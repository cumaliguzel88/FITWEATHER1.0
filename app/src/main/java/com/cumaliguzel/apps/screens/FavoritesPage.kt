package com.cumaliguzel.apps.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cumaliguzel.apps.components.ClothesCard
import com.cumaliguzel.apps.components.ClothesDetailsBottomSheet
import com.cumaliguzel.apps.components.EmptyFavoritesView
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize
import com.cumaliguzel.apps.viewModel.ClothesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesPage(clothesViewModel: ClothesViewModel) {
    val windowSize = rememberWindowSize()
    val favoriteKeys = clothesViewModel.favorites.collectAsState(emptyList())
    val clothesList by clothesViewModel.clothesList.collectAsState(emptyList())
    var selectedClothes by remember { mutableStateOf<Clothes?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    // Favori kıyafetleri `clothesList` ile eşleştir
    val favoriteClothes = clothesList.filter { clothes ->
        favoriteKeys.value.contains(clothes.id)
    }

    // Grid sütun sayısını ayarla
    val gridColumns = when (windowSize.width) {
        WindowType.Compact -> 2
        WindowType.Medium -> 3
        WindowType.Expanded -> 4
    }

    if (favoriteClothes.isEmpty()) {
        EmptyFavoritesView()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridColumns),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(favoriteClothes) { clothes ->
                ClothesCard(
                    clothes = clothes,
                    isFavorite = clothes.isFavorite,
                    onToggleFavorite = {
                        clothesViewModel.toggleFavorite(clothes)
                    },
                    onClick = {
                        selectedClothes = clothes
                        isBottomSheetVisible = true
                    }
                )
            }
        }
    }

    if (isBottomSheetVisible && selectedClothes != null) {
        val bottomSheetHeight = when (windowSize.height) {
            WindowType.Compact -> 0.6f
            WindowType.Medium -> 0.75f
            WindowType.Expanded -> 0.85f
        }

        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            modifier = Modifier.fillMaxHeight(bottomSheetHeight)
        ) {
            ClothesDetailsBottomSheet(clothes = selectedClothes!!)
        }
    }
}
