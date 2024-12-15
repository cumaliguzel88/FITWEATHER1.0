package com.cumaliguzel.apps.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.viewModel.ClothesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesPage(clothesViewModel: ClothesViewModel) {
    val favoriteKeys = clothesViewModel.favorites.collectAsState(emptyList())
    val clothesList by clothesViewModel.clothesList.collectAsState(emptyList())
    var selectedClothes by remember { mutableStateOf<Clothes?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    // Favori kıyafetleri `clothesList` ile eşleştir
    // Favori kıyafetleri `clothesList` ile eşleştir
    val favoriteClothes = clothesList.filter { clothes ->
        favoriteKeys.value.contains(clothes.id) // documentId kullanılıyor
    }


    if (favoriteClothes.isEmpty()) {
        EmptyFavoritesView()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
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
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            modifier = Modifier.fillMaxHeight(0.8f)
        ) {
            ClothesDetailsBottomSheetss(clothes = selectedClothes!!)
        }
    }
}

@Composable
fun EmptyFavoritesView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No favorites yet!",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun ClothesDetailsBottomSheetss(clothes: Clothes) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Clothes Details",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        AsyncImage(
            model = clothes.img,
            contentDescription = "Clothes Image",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )


    }
}
